/**
 * 
 */
package com.flamingos.tech.osp.batch.newsletter.writer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.mail.MailException;

import com.flamingos.osp.bean.ConfigParamBean;
import com.flamingos.osp.dto.ConfigParamDto;
import com.flamingos.osp.email.EmailGateway;
import com.flamingos.osp.email.Mail;
import com.flamingos.osp.sms.SMS;
import com.flamingos.osp.sms.SmsGateway;
import com.flamingos.osp.util.AppConstants;
import com.flamingos.osp.util.AppUtil;
import com.flamingos.osp.util.EncryptionUtil;
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
public class UserCommJobWriter implements ItemWriter<UserCommunication>, InitializingBean {

  private String threadName;

  @Autowired
  private ConfigParamBean configParamBean;
  @Autowired
  private EmailGateway emailGateway;
  @Autowired
  private SmsGateway smsGateway;

  @Value("${mail.smtp.sender.from}")
  private String mailFromAddress;
  @Value("${osp.org.name}")
  private String ORG_NAME;
  @Value("${osp.org.address}")
  private String ORG_ADDRESS;
  @Value("${osp.org.phoneno}")
  private String ORG_PHONE_NO;
  @Value("${osp.app.registration.url}")
  private String OSP_APP_REGISTRATION_URL;

  @Value("${osp.batch.valid.email.pattern}")
  private String EMAIL_PATTERN;

  ConfigParamDto oEmailChannel = null;
  ConfigParamDto oSmsChannel = null;


  private static final Logger LOGGER = Logger.getLogger(UserCommJobWriter.class);

  /**
   * @param threadName the threadName to set
   */
  public void setThreadName(String threadName) {
    this.threadName = threadName;
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    // TODO Auto-generated method stub

    oEmailChannel =
        configParamBean.getParameterByCodeName(AppConstants.PARAM_CODE_COMM_CHANNEL,
            AppConstants.PARAM_NAME_EMAIL);
    oSmsChannel =
        configParamBean.getParameterByCodeName(AppConstants.PARAM_CODE_COMM_CHANNEL,
            AppConstants.PARAM_NAME_SMS);
  }

  private NamedParameterJdbcTemplate oNPJdbcTemplate;

  @Autowired
  private JdbcTemplate jdbcTemplate;

  public void write(List<? extends UserCommunication> oCommunications) throws Exception {
    System.out.println("UserCommunication : " + oCommunications.size());
    Map<Integer, List<Mail>> lstMailsJobMap = new HashMap<Integer, List<Mail>>();
    for (UserCommunication oUserComm : oCommunications) {
      User oUser = oUserComm.getoUser();
      List<CommJobTemplate> templates = oUserComm.getLstCommJobTemplate();
      if (null != oUser && null != templates && !templates.isEmpty()) {
        for (CommJobTemplate oTemplate : templates) {
          CommTemplate oCommTemplate = oTemplate.getCommTemplate();
          CommJob oCommJob = oTemplate.getCommJob();

          if (oCommTemplate.getCommChannelId() == oEmailChannel.getParameterid()) {
            // SEND EMAIL
            LOGGER.info(threadName + " : Preparing send Email for User type : "
                + oUser.getUserType() + " ,UserId: " + oUser.getId() + " , Template: "
                + oCommTemplate.getTemplateId() + " , Job Id : " + oCommJob.getCommJobId());
            if (AppUtil.checkPattern(EMAIL_PATTERN, oUser.getEmailId())) {
              Mail oMail = new Mail();
              oMail.setMailSubject(oCommJob.getEmailSubject());
              oMail.setTemplateName(oCommTemplate.getTemplateFileName());
              oMail.setMailTo(oUser.getEmailId());
              oMail.setMailFrom(mailFromAddress);
              oMail.setFirstName(oUser.getfName());
              oMail.setMiddleName(oUser.getmName());
              oMail.setLastName(oUser.getlName());
              oMail.setEntityId(oUser.getId());
              oMail.setOrgName(ORG_NAME);
              oMail.setOrgAddress(ORG_ADDRESS);
              oMail.setOrgPhoneNo(ORG_PHONE_NO);
              oMail.setRegistrationURL(OSP_APP_REGISTRATION_URL);
              // Encrypt ID Prof_id/client_id
              oMail.setUuid(EncryptionUtil.encryption(oMail.getEntityId() + ""));

              if (null != oCommJob.getImageUrl() && !oCommJob.getImageUrl().isEmpty()) {
                oMail.getMapInlineImages().put("image1", oCommJob.getImageUrl());
              }
              List<Mail> lstMail = lstMailsJobMap.get(oCommJob.getCommJobId());
              if (null == lstMail) {
                lstMail = new ArrayList<Mail>();
                lstMailsJobMap.put(oCommJob.getCommJobId(), lstMail);
              }
              lstMail.add(oMail);
            } else {
              LOGGER.error(threadName + " : Invalid Email Id , ,UserId: " + oUser.getId()
                  + " Email Id: " + oUser.getEmailId());
              CommTemplateBuffer.getJobStatusMap().get(oCommJob.getCommJobId())
                  .incrementFailedCount(1);
            }
          } else if (oCommTemplate.getCommChannelId() == oSmsChannel.getParameterid()) {
            // SEND SMS
            try {
              LOGGER.info(threadName + " : Sending SMS for User type : " + oUser.getUserType()
                  + " ,UserId: " + oUser.getId() + " , Template: " + oCommTemplate.getTemplateId()
                  + " , Job Id : " + oCommJob.getCommJobId());
              SMS sms = new SMS();
              sms.setRecipient(oUser.getPhoneNumber());
              sms.setMessage(oCommJob.getContent().toString());
              sms.setTemplateName(oCommTemplate.getTemplateFileName());
              smsGateway.sendSms(sms);
              CommTemplateBuffer.getJobStatusMap().get(oCommJob.getCommJobId())
                  .incrementProcessedCount(1);
            } catch (Exception ex) {
              CommTemplateBuffer.getJobStatusMap().get(oCommJob.getCommJobId())
                  .incrementFailedCount(1);
            }
          }
        }
      }
    }

    for (Entry<Integer, List<Mail>> jobMailEntry : lstMailsJobMap.entrySet()) {
      List<Mail> lstMails = jobMailEntry.getValue();
      int jobId = jobMailEntry.getKey();
      try {
        emailGateway.sendBatchMail(lstMails);
        CommTemplateBuffer.getJobStatusMap().get(jobId).incrementProcessedCount(lstMails.size());
        LOGGER.info(threadName + " : Mail Sent Successfully for job id: " + jobId + " , Count: "
            + lstMails.size());
      } catch (MailException oException) {
        LOGGER.error(threadName + " : MailSending Failed: Count : " + lstMails.size()
            + " , Error Message:" + oException.getMessage());
        CommTemplateBuffer.getJobStatusMap().get(jobId).incrementFailedCount(lstMails.size());
      }
    }
  }
}
