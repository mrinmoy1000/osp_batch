/**
 * 
 */
package com.flamingos.tech.osp.batch.newsletter.listener;

import org.apache.log4j.Logger;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ExecutionContext;

/**
 * @author Mrinmoy
 *
 */
public class UserStepListener implements StepExecutionListener {

	private static final Logger logger = Logger
			.getLogger(UserStepListener.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.batch.core.StepExecutionListener#beforeStep(org.
	 * springframework.batch.core .StepExecution)
	 */
	public void beforeStep(StepExecution stepExecution) {
		// TODO Auto-generated method stub
		logger.info("Execution begening for : "
				+ stepExecution.getStepName());
		ExecutionContext oExecutionContext = stepExecution
				.getExecutionContext();
		// oExecutionContext.

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.batch.core.StepExecutionListener#afterStep(org.
	 * springframework.batch.core .StepExecution)
	 */
	public ExitStatus afterStep(StepExecution stepExecution) {
		// TODO Auto-generated method stub
		logger.info("Execution end for : "
				+ stepExecution.getStepName());
		
		return null;
	}

}
