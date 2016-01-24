/**
 * 
 */
package com.flamingos.tech.osp.batch.newsletter.writer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.flamingos.osp.bean.ConfigParamBean;
import com.flamingos.osp.dto.ConfigParamDto;
import com.flamingos.osp.util.AppConstants;
import com.flamingos.tech.osp.batch.buffer.CommTemplateBuffer;
import com.flamingos.tech.osp.batch.model.JobStatusModel;
import com.flamingos.tech.osp.batch.newsletter.model.CommJob;
import com.flamingos.tech.osp.batch.newsletter.model.CommJobTemplate;

/**
 * @author Mrinmoy
 *
 */
public class CommTemplateWriter implements ItemWriter<CommJobTemplate>, InitializingBean {

  @Autowired
  private NamedParameterJdbcTemplate oNPJdbcTemplate;

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Autowired
  private ConfigParamBean configParamBean;

  ConfigParamDto oProfessionalUser = null;

  ConfigParamDto oClientUser = null;

  ConfigParamDto oAllUser = null;

  @Value("${query_osp_newsletter_all_active_sub_category_ids}")
  private String SQL_ALL_ACTIVE_SUB_CAT;

  @Value("${query_osp_newsletter_all_active_sub_category_ids_in_cat_ids}")
  private String SQL_SELECTIVE_ACTIVE_SUB_CAT;

  /*
   * (non-Javadoc)
   * 
   * @see org.springframework.batch.item.ItemWriter#write(java.util.List)
   */
  public void write(List<? extends CommJobTemplate> oCommJobTemplates) throws Exception {
    /**
     * Buffer communication templates indexed by user type,role_status.
     */
    List<ConfigParamDto> activeUserStatusParam =
        configParamBean.getParametersByCode(AppConstants.PARAM_CODE_USER_STATUS);
    List<Integer> activeUserStatus = new ArrayList<Integer>();
    if (null != activeUserStatusParam) {
      for (ConfigParamDto oConfigParamDto : activeUserStatusParam) {
        activeUserStatus.add(oConfigParamDto.getParameterid());
      }
    }

    for (CommJobTemplate oCommJobTemplate : oCommJobTemplates) {
      CommJob oCommJob = oCommJobTemplate.getCommJob();
      String jobTemplateKey = oCommJob.getTemplateId() + "_" + oCommJob.getTemplateId();
      CommTemplateBuffer.getJobTemplateIdMap().put(jobTemplateKey, oCommJobTemplate);
      CommTemplateBuffer.getJobStatusMap().put(
          oCommJob.getCommJobId(),
          new JobStatusModel(oCommJob.getCommJobId(), oCommJob.getJobStatus(),
              AppConstants.MESSAGE_COMM_JOB_INITIAL));
      /*
       * For Professional key is Sub Category id.. All represents all sub category
       */
      if ((oCommJob.getTargetUser() == oProfessionalUser.getParameterid()) // PROFESSIONAL
          || (oCommJob.getTargetUser() == oAllUser.getParameterid())) { // ALL
        if (!oCommJob.isTargetUserAllStatus()) {
          CommTemplateBuffer.getProfessionalJobStatusSet()
              .addAll(oCommJob.getLstTargetUserStatus());
        } else {
          CommTemplateBuffer.getProfessionalJobStatusSet().addAll(activeUserStatus);
        }
        Map<Integer, List<CommJobTemplate>> profJobTemplates =
            CommTemplateBuffer.getTemplateForProfessionals();
        List<Integer> activeSubCat = null;
        if (oCommJob.isTargetUserAllCat()) {
          activeSubCat = jdbcTemplate.queryForList(SQL_ALL_ACTIVE_SUB_CAT, Integer.class);
        } else if (!oCommJob.getLstTargetUserCatIds().isEmpty()) {
          StringBuffer inCatIds = new StringBuffer();
          for (Integer catId : oCommJob.getLstTargetUserCatIds()) {
            inCatIds.append(catId).append(',');
          }
          inCatIds.deleteCharAt(inCatIds.length() - 1);
          Object[] args = new Object[] {inCatIds};
          activeSubCat =
              jdbcTemplate.queryForList(SQL_SELECTIVE_ACTIVE_SUB_CAT, args, Integer.class);
        }
        if (null != activeSubCat && !activeSubCat.isEmpty()) {
          if (!oCommJob.isTargetUserAllSubCat() && !oCommJob.getLstTargetUserSubCatIds().isEmpty()) {
            activeSubCat.retainAll(oCommJob.getLstTargetUserSubCatIds());
          }
          if (!activeSubCat.isEmpty()) {
            for (Integer subCatId : activeSubCat) {
              List<CommJobTemplate> subCatList = profJobTemplates.get(subCatId);
              if (null == subCatList) {
                subCatList = new ArrayList<CommJobTemplate>();
                profJobTemplates.put(subCatId, subCatList);
              }
              subCatList.add(oCommJobTemplate);
            }
            CommTemplateBuffer.getProfessionalJobSubCatSet().addAll(activeSubCat);
          }
        }
      }

      // Assuming Client can be only ALL. i.e. All types of clients will
      // receive all emails for category ALl and Client.
      if ((oCommJob.getTargetUser() == oClientUser.getParameterid()) // CLIENT
          || (oCommJob.getTargetUser() == oAllUser.getParameterid())) {// ALL
        if (!oCommJob.isTargetUserAllStatus()) {
          CommTemplateBuffer.getCientJobStatusSet().addAll(oCommJob.getLstTargetUserStatus());
        } else {
          CommTemplateBuffer.getCientJobStatusSet().addAll(activeUserStatus);
        }
        Map<String, List<CommJobTemplate>> clientJobTemplates =
            CommTemplateBuffer.getTemplateForClients();
        List<CommJobTemplate> allClientJobTempList = clientJobTemplates.get(AppConstants.KEY_ALL);
        if (null == allClientJobTempList) {
          allClientJobTempList = new ArrayList<CommJobTemplate>();
          clientJobTemplates.put(AppConstants.KEY_ALL, allClientJobTempList);
        }
        allClientJobTempList.add(oCommJobTemplate);
      }
    }
    System.out.println("");
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    // TODO Auto-generated method stub
    oProfessionalUser =
        configParamBean.getParameterByCodeName(AppConstants.PARAM_CODE_USER_TYPE,
            AppConstants.PARAM_NAME_PROFESSIONAL);
    oClientUser =
        configParamBean.getParameterByCodeName(AppConstants.PARAM_CODE_USER_TYPE,
            AppConstants.PARAM_NAME_CLIENT);
    oAllUser =
        configParamBean.getParameterByCodeName(AppConstants.PARAM_CODE_USER_TYPE,
            AppConstants.PARAM_NAME_ALL);
  }

}
