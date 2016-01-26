/**
 * 
 */
package com.flamingos.tech.osp.batch.newsletter.processor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import com.flamingos.osp.bean.ConfigParamBean;
import com.flamingos.osp.dto.ConfigParamDto;
import com.flamingos.osp.util.AppConstants;
import com.flamingos.tech.osp.batch.buffer.CommTemplateBuffer;
import com.flamingos.tech.osp.batch.model.User;
import com.flamingos.tech.osp.batch.newsletter.model.CommJob;
import com.flamingos.tech.osp.batch.newsletter.model.CommJobTemplate;
import com.flamingos.tech.osp.batch.newsletter.model.CommTemplate;
import com.flamingos.tech.osp.batch.newsletter.model.UserCommunication;

/**
 * @author Mrinmoy
 *
 */
public class UserCommProcessor implements ItemProcessor<User, UserCommunication>, InitializingBean {

  private String threadName;

  /**
   * @param threadName the threadName to set
   */
  public void setThreadName(String threadName) {
    this.threadName = threadName;
  }

  @Autowired
  private ConfigParamBean configParamBean;

  ConfigParamDto oProfessionalUser = null;

  ConfigParamDto oClientUser = null;
  ConfigParamDto oEmailChannel = null;
  ConfigParamDto oSmsChannel = null;


  @Override
  public void afterPropertiesSet() throws Exception {
    oProfessionalUser =
        configParamBean.getParameterByCodeName(AppConstants.PARAM_CODE_USER_TYPE,
            AppConstants.PARAM_NAME_PROFESSIONAL);
    oClientUser =
        configParamBean.getParameterByCodeName(AppConstants.PARAM_CODE_USER_TYPE,
            AppConstants.PARAM_NAME_CLIENT);
    oEmailChannel =
        configParamBean.getParameterByCodeName(AppConstants.PARAM_CODE_COMM_CHANNEL,
            AppConstants.PARAM_NAME_EMAIL);
    oSmsChannel =
        configParamBean.getParameterByCodeName(AppConstants.PARAM_CODE_COMM_CHANNEL,
            AppConstants.PARAM_NAME_SMS);
  }

  public UserCommunication process(User user) throws Exception {
    UserCommunication communication = null;
    /*
     * Logic to find All applicable templates for the user. If no matching template found, return
     * null..(i.e. skip the user)
     */
    List<CommJobTemplate> applicabelTemplates = new ArrayList<CommJobTemplate>();
    List<CommJobTemplate> finalApplicabelTemplates = new ArrayList<CommJobTemplate>();
    if (user.getUserType() == oProfessionalUser.getParameterid()) {
      Map<Integer, List<CommJobTemplate>> profTemplates =
          CommTemplateBuffer.getTemplateForProfessionals();

      if (null != profTemplates.get(user.getSubCategoryId())) {
        applicabelTemplates.addAll(profTemplates.get(user.getSubCategoryId()));
      }
    } else if (user.getUserType() == oClientUser.getParameterid()) {
      Map<String, List<CommJobTemplate>> clientTemplates =
          CommTemplateBuffer.getTemplateForClients();
      if (null != clientTemplates.get(AppConstants.KEY_ALL)) {// All Segment Templates.
        applicabelTemplates.addAll(clientTemplates.get(AppConstants.KEY_ALL));
      }
    }
    if (!applicabelTemplates.isEmpty()) {
      finalApplicabelTemplates = discardUnsubscribedAndRepeatTemplate(user, applicabelTemplates);
    }
    if (!finalApplicabelTemplates.isEmpty()) {
      communication = new UserCommunication();
      communication.setLstCommJobTemplate(finalApplicabelTemplates);
      communication.setoUser(user);
    }
    return communication;
  }

  private List<CommJobTemplate> discardUnsubscribedAndRepeatTemplate(User user,
      List<CommJobTemplate> applicabelTemplates) {
    List<CommJobTemplate> finalApplicableTemplates = new ArrayList<CommJobTemplate>();
    for (CommJobTemplate oJobTemplate : applicabelTemplates) {
      CommJob oCommJob = oJobTemplate.getCommJob();
      CommTemplate oTemplate = oJobTemplate.getCommTemplate();
      if ((oCommJob.isTargetUserAllStatus() || oCommJob.getLstTargetUserStatus().contains(
          user.getStatus()))
          && oCommJob.getLstTargetUserStatus().contains(user.getStatus())) {
        if (oTemplate.getCommChannelId() == oEmailChannel.getParameterid()
            && user.getSubscriptionsCat().contains(oTemplate.getTemplateSubCatId())) {
          finalApplicableTemplates.add(oJobTemplate);
        } else if (oTemplate.getCommChannelId() == oSmsChannel.getParameterid()
            && null != user.getPhoneNumber() && !user.isDndActivated()) {
          finalApplicableTemplates.add(oJobTemplate);
        }

        CommTemplateBuffer.getJobStatusMap().get(oCommJob.getCommJobId()).incrementTotalNoToSent();
      }
    }
    return finalApplicableTemplates;
  }
}
