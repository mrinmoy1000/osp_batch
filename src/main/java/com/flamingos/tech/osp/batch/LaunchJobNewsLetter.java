package com.flamingos.tech.osp.batch;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class LaunchJobNewsLetter {
  public static void main(String[] args) {

    String[] springConfig = {"spring/batch/jobs/newslettersms-job.xml"};

    ApplicationContext context = new ClassPathXmlApplicationContext(springConfig);
    Map<String, JobParameter> jobParams = new HashMap<String, JobParameter>();
    if (args.length == 0) {
      jobParams.put("param_jobStartTime", new JobParameter(new Date()));
    } else {
      jobParams.put("job_execution_id", new JobParameter(args[0]));
    }
    JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
    Job job = (Job) context.getBean("partitionNewsLetterSmsJob");

    try {

      JobExecution execution = jobLauncher.run(job, new JobParameters(jobParams));
      System.out.println("Exit Status : " + execution.getStatus());

    } catch (Exception e) {
      e.printStackTrace();
    }

    System.out.println("Done");

  }
}
