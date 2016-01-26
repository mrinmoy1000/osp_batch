package com.flamingos.tech.osp.batch;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class LaunchJobNewsLetter {
  private static final Logger logger = Logger.getLogger(LaunchJobNewsLetter.class);

  public static void main(String[] args) {

    String[] springConfig = {"spring/batch/jobs/newslettersms-job.xml"};

    ApplicationContext context = new ClassPathXmlApplicationContext(springConfig);
    Map<String, JobParameter> jobParams = new HashMap<String, JobParameter>();
    if (args.length == 1) {
      jobParams.put("param_jobStartTime", new JobParameter(new Date()));
    } else {
      jobParams.put("job_execution_id", new JobParameter(args[1]));
    }
    JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
    Job job = (Job) context.getBean("partitionNewsLetterSmsJob");

    try {

      JobExecution execution = jobLauncher.run(job, new JobParameters(jobParams));
      logger.info("Exit Status : " + execution.getStatus());

    } catch (Exception e) {
      e.printStackTrace();
      logger.error(e.getCause());
    }

    logger.info("Done");

  }
}
