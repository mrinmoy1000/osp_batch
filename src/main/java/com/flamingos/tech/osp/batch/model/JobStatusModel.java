/**
 * 
 */
package com.flamingos.tech.osp.batch.model;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Mrinmoy
 *
 */
public class JobStatusModel {
  private int commJobId;
  private int jobStatus;
  private String message;
  private AtomicInteger processedCount = new AtomicInteger();
  private AtomicInteger failedCount = new AtomicInteger();
  private AtomicInteger totalNoToSent = new AtomicInteger();

  /**
   * @param jobId
   * @param jobStatus
   * @param message
   */
  public JobStatusModel(int commJobId, int jobStatus, String message) {
    this.commJobId = commJobId;
    this.jobStatus = jobStatus;
    this.message = message;
  }

  /**
   * @return the commJobId
   */
  public int getCommJobId() {
    return commJobId;
  }

  /**
   * @param commJobId the commJobId to set
   */
  public void setCommJobId(int commJobId) {
    this.commJobId = commJobId;
  }

  /**
   * @return the jobStatus
   */
  public int getJobStatus() {
    return jobStatus;
  }

  /**
   * @param jobStatus the jobStatus to set
   */
  public void setJobStatus(int jobStatus) {
    this.jobStatus = jobStatus;
  }

  /**
   * @return the message
   */
  public String getMessage() {
    return message;
  }

  /**
   * @param message the message to set
   */
  public void setMessage(String message) {
    this.message = message;
  }

  /**
   * @return the processedCount
   */
  public int getProcessedCount() {
    return processedCount.intValue();
  }

  /**
   * Increments Processed Count by delta
   * 
   * @param delta
   * @return
   */
  public int incrementProcessedCount(int delta) {
    return this.processedCount.getAndAdd(delta);
  }

  /**
   * @return the failedCount
   */
  public int getFailedCount() {
    return failedCount.intValue();
  }

  /**
   * Increments Failed Count by delta
   * 
   * @param delta
   * @return
   */
  public int incrementFailedCount(int delta) {
    return this.failedCount.getAndAdd(delta);
  }

  /**
   * @return the totalNoToSent
   */
  public int getTotalNoToSent() {
    return totalNoToSent.intValue();
  }

  /**
   * Increments Total number of email/sms to send for the job id.
   *
   * @return
   */
  public int incrementTotalNoToSent() {
    return this.totalNoToSent.incrementAndGet();
  }

}
