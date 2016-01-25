/**
 * 
 */
package com.flamingos.tech.osp.batch.newsletter.listener;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.flamingos.osp.bean.ConfigParamBean;
import com.flamingos.osp.dto.ConfigParamDto;
import com.flamingos.osp.util.AppConstants;
import com.flamingos.tech.osp.batch.buffer.CommTemplateBuffer;
import com.flamingos.tech.osp.batch.model.JobStatusModel;

/**
 * @author Mrinmoy
 *
 */
public class NewsLetterJobListener implements JobExecutionListener, InitializingBean {

  @Value("${osp.batch.newsletter.desired.success.percentage}")
  private int desiredSuccessPersentage;

  @Value("${query_osp_newsletter_update_comm_job_status}")
  private String SQL_UPDATE_COMM_JOB_STATUS;

  @Value("${osp.batch.user}")
  private String BATCH_USER;

  @Autowired
  private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  @Autowired
  private ConfigParamBean configParamBean;
  
  ConfigParamDto oJobStatusProcessed = null;
  ConfigParamDto oJobStatusFailed = null;
  
  private static final Logger logger = Logger.getLogger(NewsLetterJobListener.class);
  
  

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.springframework.batch.core.JobExecutionListener#beforeJob(org.springframework.batch.core
   * .JobExecution)
   */
  public void beforeJob(JobExecution jobExecution) {
    // TODO Auto-generated method stub
    logger.info("Newsletter Job Starting");
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.springframework.batch.core.JobExecutionListener#afterJob(org.springframework.batch.core
   * .JobExecution)
   */
  public void afterJob(JobExecution jobExecution) {
    // TODO Auto-generated method stub
    Set<Entry<Integer, JobStatusModel>> jobMap = CommTemplateBuffer.getJobStatusMap().entrySet();
    if (!jobMap.isEmpty()) {
      if (jobExecution.getExitStatus().equals(ExitStatus.COMPLETED)) {
        for (Entry<Integer, JobStatusModel> jobStatusModelEntry : jobMap) {
          JobStatusModel jobStatusModel = jobStatusModelEntry.getValue();
          if (jobStatusModel.getTotalNoToSent() == (jobStatusModel.getProcessedCount() + jobStatusModel
              .getFailedCount())) {
            // Total number to send count matches total failed and processed count.
            double successRatio =
                ((double)jobStatusModel.getProcessedCount() / jobStatusModel.getTotalNoToSent()) * 100;
            if (successRatio >= desiredSuccessPersentage) {
              // Update Job Status as processed.
              updateJobStatus(jobStatusModel.getCommJobId(),oJobStatusProcessed.getParameterid());
            } else {
              // Update Job Status as Failed.
              updateJobStatus(jobStatusModel.getCommJobId(),oJobStatusFailed.getParameterid());
            }
          } else {
            // Update Job as Failed.
            updateJobStatus(jobStatusModel.getCommJobId(),oJobStatusFailed.getParameterid());
          }
        }
         logger.info("Newsletter Job Executed");
      } else {

      }
    }
  }

  private void updateJobStatus(int jobId, int status) {
    Map<String, Object> paramMap = new HashMap<String, Object>();
    paramMap.put("jobStatus", status);
    paramMap.put("updatedBy", BATCH_USER);
    paramMap.put("jobId", jobId);
    namedParameterJdbcTemplate.update(SQL_UPDATE_COMM_JOB_STATUS, paramMap);
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    oJobStatusProcessed =
        configParamBean.getParameterByCodeName(AppConstants.PARAM_CODE_JOB_STATUS,
            AppConstants.PARAM_NAME_PROCESSED);
    oJobStatusFailed =
        configParamBean.getParameterByCodeName(AppConstants.PARAM_CODE_JOB_STATUS,
            AppConstants.PARAM_NAME_FAILES);
  }
}
