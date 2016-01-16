/**
 * 
 */
package com.flamingos.tech.osp.batch.newsletter.model;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Mrinmoy
 *
 */
public class CommJob {
	private int commJobId;
	private int templateId;
	private int targetUser;
	private String targetUserCat;
	private List<Integer> lstTargetUserCatIds = new ArrayList<Integer>();
	private boolean isTargetUserAllCat = false;
	private String targetUserSubCat;
	private List<Integer> lstTargetUserSubCatIds = new ArrayList<Integer>();
	private boolean isTargetUserAllSubCat = false;
	private List<Integer> lstTargetUserStatus=new ArrayList<Integer>();
	private boolean isTargetUserAllStatus=false;
	private String emailSubject;
	private Blob content;
	private String imageUrl;
	private boolean isImplicitJob=false;
	private Date jobDate;
	private int jobStatus;
	private String createdBy;
	private Date createdTs;
	private String updatedBy;
	private Date updatedTs;
	

	/**
	 * @return the commJobId
	 */
	public int getCommJobId() {
		return commJobId;
	}

	/**
	 * @param commJobId
	 *            the commJobId to set
	 */
	public void setCommJobId(int commJobId) {
		this.commJobId = commJobId;
	}

	/**
	 * @return the templateId
	 */
	public int getTemplateId() {
		return templateId;
	}

	/**
	 * @param templateId
	 *            the templateId to set
	 */
	public void setTemplateId(int templateId) {
		this.templateId = templateId;
	}

	/**
	 * @return the targetUser
	 */
	public int getTargetUser() {
		return targetUser;
	}

	/**
	 * @param targetUser
	 *            the targetUser to set
	 */
	public void setTargetUser(int targetUser) {
		this.targetUser = targetUser;
	}

	/**
	 * @return the targetUserCat
	 */
	public String getTargetUserCat() {
		return targetUserCat;
	}

	/**
	 * @param targetUserCat
	 *            the targetUserCat to set
	 */
	public void setTargetUserCat(String targetUserCat) {
		this.targetUserCat = targetUserCat;
	}

	/**
	 * @return the lstTargetUserCatIds
	 */
	public List<Integer> getLstTargetUserCatIds() {
		return lstTargetUserCatIds;
	}

	/**
	 * @param lstTargetUserCatIds
	 *            the lstTargetUserCatIds to set
	 */
	public void setLstTargetUserCatIds(List<Integer> lstTargetUserCatIds) {
		this.lstTargetUserCatIds = lstTargetUserCatIds;
	}

	/**
	 * @return the isTargetUserAllCat
	 */
	public boolean isTargetUserAllCat() {
		return isTargetUserAllCat;
	}

	/**
	 * @param isTargetUserAllCat
	 *            the isTargetUserAllCat to set
	 */
	public void setTargetUserAllCat(boolean isTargetUserAllCat) {
		this.isTargetUserAllCat = isTargetUserAllCat;
	}

	/**
	 * @return the targetUserSubCat
	 */
	public String getTargetUserSubCat() {
		return targetUserSubCat;
	}

	/**
	 * @param targetUserSubCat
	 *            the targetUserSubCat to set
	 */
	public void setTargetUserSubCat(String targetUserSubCat) {
		this.targetUserSubCat = targetUserSubCat;
	}

	/**
	 * @return the lstTargetUserSubCatIds
	 */
	public List<Integer> getLstTargetUserSubCatIds() {
		return lstTargetUserSubCatIds;
	}

	/**
	 * @param lstTargetUserSubCatIds
	 *            the lstTargetUserSubCatIds to set
	 */
	public void setLstTargetUserSubCatIds(List<Integer> lstTargetUserSubCatIds) {
		this.lstTargetUserSubCatIds = lstTargetUserSubCatIds;
	}

	/**
	 * @return the isTargetUserAllSubCat
	 */
	public boolean isTargetUserAllSubCat() {
		return isTargetUserAllSubCat;
	}

	/**
	 * @param isTargetUserAllSubCat
	 *            the isTargetUserAllSubCat to set
	 */
	public void setTargetUserAllSubCat(boolean isTargetUserAllSubCat) {
		this.isTargetUserAllSubCat = isTargetUserAllSubCat;
	}

	/**
	 * @return the lstTargetUserStatus
	 */
	public List<Integer> getLstTargetUserStatus() {
		return lstTargetUserStatus;
	}

	/**
	 * @param lstTargetUserStatus the lstTargetUserStatus to set
	 */
	public void setLstTargetUserStatus(List<Integer> lstTargetUserStatus) {
		this.lstTargetUserStatus = lstTargetUserStatus;
	}

	/**
	 * @return the isTargetUserAllStatus
	 */
	public boolean isTargetUserAllStatus() {
		return isTargetUserAllStatus;
	}

	/**
	 * @param isTargetUserAllStatus the isTargetUserAllStatus to set
	 */
	public void setTargetUserAllStatus(boolean isTargetUserAllStatus) {
		this.isTargetUserAllStatus = isTargetUserAllStatus;
	}

	/**
	 * @return the emailSubject
	 */
	public String getEmailSubject() {
		return emailSubject;
	}

	/**
	 * @param emailSubject
	 *            the emailSubject to set
	 */
	public void setEmailSubject(String emailSubject) {
		this.emailSubject = emailSubject;
	}

	/**
	 * @return the content
	 */
	public Blob getContent() {
		return content;
	}

	/**
	 * @param content
	 *            the content to set
	 */
	public void setContent(Blob content) {
		this.content = content;
	}

	/**
	 * @return the imageUrl
	 */
	public String getImageUrl() {
		return imageUrl;
	}

	/**
	 * @param imageUrl
	 *            the imageUrl to set
	 */
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	/**
	 * @return the isImplicitJob
	 */
	public boolean isImplicitJob() {
		return isImplicitJob;
	}

	/**
	 * @param isImplicitJob the isImplicitJob to set
	 */
	public void setImplicitJob(boolean isImplicitJob) {
		this.isImplicitJob = isImplicitJob;
	}

	/**
	 * @return the jobDate
	 */
	public Date getJobDate() {
		return jobDate;
	}

	/**
	 * @param jobDate
	 *            the jobDate to set
	 */
	public void setJobDate(Date jobDate) {
		this.jobDate = jobDate;
	}

	/**
	 * @return the jobStatus
	 */
	public int getJobStatus() {
		return jobStatus;
	}

	/**
	 * @param jobStatus
	 *            the jobStatus to set
	 */
	public void setJobStatus(int jobStatus) {
		this.jobStatus = jobStatus;
	}

	/**
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy
	 *            the createdBy to set
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
	 * @param createdTs
	 *            the createdTs to set
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
	 * @param updatedBy
	 *            the updatedBy to set
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
	 * @param updatedTs
	 *            the updatedTs to set
	 */
	public void setUpdatedTs(Date updatedTs) {
		this.updatedTs = updatedTs;
	}
}