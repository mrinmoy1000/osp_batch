/**
 * 
 */
package com.flamingos.tech.osp.batch.newsletter.listener;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

/**
 * @author Mrinmoy
 *
 */
public class NewsLetterJobListener implements JobExecutionListener {

	/* (non-Javadoc)
	 * @see org.springframework.batch.core.JobExecutionListener#beforeJob(org.springframework.batch.core.JobExecution)
	 */
	public void beforeJob(JobExecution jobExecution) {
		// TODO Auto-generated method stub
		System.out.println("Newsletter Job Starting");
	}

	/* (non-Javadoc)
	 * @see org.springframework.batch.core.JobExecutionListener#afterJob(org.springframework.batch.core.JobExecution)
	 */
	public void afterJob(JobExecution jobExecution) {
		// TODO Auto-generated method stub
		System.out.println("Newsletter Job Executed");
	}

}
