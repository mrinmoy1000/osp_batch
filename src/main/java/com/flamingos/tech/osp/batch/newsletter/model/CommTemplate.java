/**
 * 
 */
package com.flamingos.tech.osp.batch.newsletter.model;

import java.util.Date;

/**
 * @author Mrinmoy
 *
 */
public class CommTemplate {
  /** Template Attributes */
  private int templateId;
  private String templateName;
  private int commChannelId;
  private int templateCatId;
  private int templateSubCatId;
  private String templateFileName;
  private Boolean isEditable;
  private boolean isActive;
  private String createdBy;
  private Date createdTs;
  private String updatedBy;
  private Date updatedTs;

  /**
   * @return the templateId
   */
  public int getTemplateId() {
    return templateId;
  }

  /**
   * @param templateId the templateId to set
   */
  public void setTemplateId(int templateId) {
    this.templateId = templateId;
  }

  /**
   * @return the templateName
   */
  public String getTemplateName() {
    return templateName;
  }

  /**
   * @param templateName the templateName to set
   */
  public void setTemplateName(String templateName) {
    this.templateName = templateName;
  }

  /**
   * @return the commChannelId
   */
  public int getCommChannelId() {
    return commChannelId;
  }

  /**
   * @param commChannelId the commChannelId to set
   */
  public void setCommChannelId(int commChannelId) {
    this.commChannelId = commChannelId;
  }

  /**
   * @return the templateCatId
   */
  public int getTemplateCatId() {
    return templateCatId;
  }

  /**
   * @param templateCatId the templateCatId to set
   */
  public void setTemplateCatId(int templateCatId) {
    this.templateCatId = templateCatId;
  }

  /**
   * @return the templateSubCatId
   */
  public int getTemplateSubCatId() {
    return templateSubCatId;
  }

  /**
   * @param templateSubCatId the templateSubCatId to set
   */
  public void setTemplateSubCatId(int templateSubCatId) {
    this.templateSubCatId = templateSubCatId;
  }

  /**
   * @return the templateFileName
   */
  public String getTemplateFileName() {
    return templateFileName;
  }

  /**
   * @param templateFileName the templateFileName to set
   */
  public void setTemplateFileName(String templateFileName) {
    this.templateFileName = templateFileName;
  }

  /**
   * @return the isEditable
   */
  public Boolean getIsEditable() {
    return isEditable;
  }

  /**
   * @param isEditable the isEditable to set
   */
  public void setIsEditable(Boolean isEditable) {
    this.isEditable = isEditable;
  }

  /**
   * @return the isActive
   */
  public boolean isActive() {
    return isActive;
  }

  /**
   * @param isActive the isActive to set
   */
  public void setActive(boolean isActive) {
    this.isActive = isActive;
  }

  /**
   * @return the createdBy
   */
  public String getCreatedBy() {
    return createdBy;
  }

  /**
   * @param createdBy the createdBy to set
   */
  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  /**
   * @return the createdTs
   */
  public Date getCreatedTs() {
    return createdTs;
  }

  /**
   * @param createdTs the createdTs to set
   */
  public void setCreatedTs(Date createdTs) {
    this.createdTs = createdTs;
  }

  /**
   * @return the updatedBy
   */
  public String getUpdatedBy() {
    return updatedBy;
  }

  /**
   * @param updatedBy the updatedBy to set
   */
  public void setUpdatedBy(String updatedBy) {
    this.updatedBy = updatedBy;
  }

  /**
   * @return the updatedTs
   */
  public Date getUpdatedTs() {
    return updatedTs;
  }

  /**
   * @param updatedTs the updatedTs to set
   */
  public void setUpdatedTs(Date updatedTs) {
    this.updatedTs = updatedTs;
  }

}
