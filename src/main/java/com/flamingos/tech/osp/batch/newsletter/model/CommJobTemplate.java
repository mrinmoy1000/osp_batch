/**
 * 
 */
package com.flamingos.tech.osp.batch.newsletter.model;

/**
 * @author Mrinmoy
 *
 */
public class CommJobTemplate {
  private CommTemplate commTemplate;
  private CommJob commJob;

  /**
   * @return the commTemplate
   */
  public CommTemplate getCommTemplate() {
    return commTemplate;
  }

  /**
   * @param commTemplate the commTemplate to set
   */
  public void setCommTemplate(CommTemplate commTemplate) {
    this.commTemplate = commTemplate;
  }

  /**
   * @return the commJob
   */
  public CommJob getCommJob() {
    return commJob;
  }

  /**
   * @param commJob the commJob to set
   */
  public void setCommJob(CommJob commJob) {
    this.commJob = commJob;
  }
}
