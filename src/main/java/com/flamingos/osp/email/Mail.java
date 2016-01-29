package com.flamingos.osp.email;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Mail {

  /**
   * 
   */
  private String mailFrom;

  private String mailTo;

  private String mailCc;

  private String mailBcc;

  private String mailSubject;

  private String mailContent;

  private String templateName;

  private String contentType;

  private String firstName;

  private String middleName;

  private String lastName;

  private String orgAddress;

  private String orgPhoneNo;

  private String orgName;

  private String uuid;

  private String registrationURL;

  private long entityId;



  private Map<String, String> mapInlineImages;

  public Mail() {
    contentType = "text/html";
    mapInlineImages = new HashMap<String, String>();
  }

  public String getContentType() {
    return contentType;
  }

  public void setContentType(String contentType) {
    this.contentType = contentType;
  }

  public String getMailBcc() {
    return mailBcc;
  }

  public String getTemplateName() {
    return templateName;
  }

  public void setTemplateName(String templateName) {
    this.templateName = templateName;
  }

  public void setMailBcc(String mailBcc) {
    this.mailBcc = mailBcc;
  }

  public String getMailCc() {
    return mailCc;
  }

  public void setMailCc(String mailCc) {
    this.mailCc = mailCc;
  }

  public String getMailFrom() {
    return mailFrom;
  }

  public void setMailFrom(String mailFrom) {
    this.mailFrom = mailFrom;
  }

  public String getMailSubject() {
    return mailSubject;
  }

  public void setMailSubject(String mailSubject) {
    this.mailSubject = mailSubject;
  }

  public String getMailTo() {
    return mailTo;
  }

  public void setMailTo(String mailTo) {
    this.mailTo = mailTo;
  }

  public Date getMailSendDate() {
    return new Date();
  }

  public String getMailContent() {
    return mailContent;
  }

  public void setMailContent(String mailContent) {
    this.mailContent = mailContent;
  }

  public Map<String, String> getMapInlineImages() {
    return mapInlineImages;
  }

  public void setMapInlineImages(Map<String, String> mapInlineImages) {
    this.mapInlineImages = mapInlineImages;
  }

  @Override
  public String toString() {
    StringBuilder lBuilder = new StringBuilder();
    lBuilder.append("Mail From:- ").append(getMailFrom());
    lBuilder.append("Mail To:- ").append(getMailTo());
    lBuilder.append("Mail Cc:- ").append(getMailCc());
    lBuilder.append("Mail Bcc:- ").append(getMailBcc());
    lBuilder.append("Mail Subject:- ").append(getMailSubject());
    lBuilder.append("Mail Send Date:- ").append(getMailSendDate());
    lBuilder.append("Mail Content:- ").append(getMailContent());
    return lBuilder.toString();
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getMiddleName() {
    return middleName;
  }

  public void setMiddleName(String middleName) {
    this.middleName = middleName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  /**
   * @return the orgAddress
   */
  public String getOrgAddress() {
    return orgAddress;
  }

  /**
   * @param orgAddress the orgAddress to set
   */
  public void setOrgAddress(String orgAddress) {
    this.orgAddress = orgAddress;
  }

  /**
   * @return the orgPhoneNo
   */
  public String getOrgPhoneNo() {
    return orgPhoneNo;
  }

  /**
   * @param orgPhoneNo the orgPhoneNo to set
   */
  public void setOrgPhoneNo(String orgPhoneNo) {
    this.orgPhoneNo = orgPhoneNo;
  }

  /**
   * @return the orgName
   */
  public String getOrgName() {
    return orgName;
  }

  /**
   * @param orgName the orgName to set
   */
  public void setOrgName(String orgName) {
    this.orgName = orgName;
  }

  /**
   * @return the uuid
   */
  public String getUuid() {
    return uuid;
  }

  /**
   * @param uuid the uuid to set
   */
  public void setUuid(String uuid) {
    this.uuid = uuid;
  }

  /**
   * @return the entityId
   */
  public long getEntityId() {
    return entityId;
  }

  /**
   * @param entityId the entityId to set
   */
  public void setEntityId(long entityId) {
    this.entityId = entityId;
  }

  /**
   * @return the registrationURL
   */
  public String getRegistrationURL() {
    return registrationURL;
  }

  /**
   * @param registrationURL the registrationURL to set
   */
  public void setRegistrationURL(String registrationURL) {
    this.registrationURL = registrationURL;
  }
}
