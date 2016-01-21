/**
 * 
 */
package com.flamingos.tech.osp.batch.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mrinmoy
 *
 */
public class User {
  private long id;
  private int userType;
  private String userId;
  private String fName;
  private String mName;
  private String lName;
  private String phoneNumber;
  private String emailId;
  private int roleId;
  private int status;
  private int categoryId;
  private int subCategoryId;
  private List<Integer> subscriptionsCat = new ArrayList<Integer>();
  private boolean dndActivated;

  /**
   * @return the id
   */
  public long getId() {
    return id;
  }

  /**
   * @param id the id to set
   */
  public void setId(long id) {
    this.id = id;
  }

  /**
   * @return the userType
   */
  public int getUserType() {
    return userType;
  }

  /**
   * @param userType the userType to set
   */
  public void setUserType(int userType) {
    this.userType = userType;
  }

  /**
   * @return the userId
   */
  public String getUserId() {
    return userId;
  }

  /**
   * @param userId the userId to set
   */
  public void setUserId(String userId) {
    this.userId = userId;
  }

  /**
   * @return the fName
   */
  public String getfName() {
    return fName;
  }

  /**
   * @param fName the fName to set
   */
  public void setfName(String fName) {
    this.fName = fName;
  }

  /**
   * @return the mName
   */
  public String getmName() {
    return mName;
  }

  /**
   * @param mName the mName to set
   */
  public void setmName(String mName) {
    this.mName = mName;
  }

  /**
   * @return the lName
   */
  public String getlName() {
    return lName;
  }

  /**
   * @param lName the lName to set
   */
  public void setlName(String lName) {
    this.lName = lName;
  }

  /**
   * @return the phoneNumber
   */
  public String getPhoneNumber() {
    return phoneNumber;
  }

  /**
   * @param phoneNumber the phoneNumber to set
   */
  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  /**
   * @return the emailId
   */
  public String getEmailId() {
    return emailId;
  }

  /**
   * @param emailId the emailId to set
   */
  public void setEmailId(String emailId) {
    this.emailId = emailId;
  }

  /**
   * @return the roleId
   */
  public int getRoleId() {
    return roleId;
  }

  /**
   * @param roleId the roleId to set
   */
  public void setRoleId(int roleId) {
    this.roleId = roleId;
  }

  /**
   * @return the status
   */
  public int getStatus() {
    return status;
  }

  /**
   * @param status the status to set
   */
  public void setStatus(int status) {
    this.status = status;
  }

  /**
   * @return the categoryId
   */
  public int getCategoryId() {
    return categoryId;
  }

  /**
   * @param categoryId the categoryId to set
   */
  public void setCategoryId(int categoryId) {
    this.categoryId = categoryId;
  }

  /**
   * @return the subCategoryId
   */
  public int getSubCategoryId() {
    return subCategoryId;
  }

  /**
   * @param subCategoryId the subCategoryId to set
   */
  public void setSubCategoryId(int subCategoryId) {
    this.subCategoryId = subCategoryId;
  }

  /**
   * @return the subscriptionsCat
   */
  public List<Integer> getSubscriptionsCat() {
    return subscriptionsCat;
  }

  /**
   * @param subscriptionsCat the subscriptionsCat to set
   */
  public void setSubscriptionsCat(List<Integer> subscriptionsCat) {
    this.subscriptionsCat = subscriptionsCat;
  }

  /**
   * @return the dndActivated
   */
  public boolean isDndActivated() {
    return dndActivated;
  }

  /**
   * @param dndActivated the dndFlag to set
   */
  public void setDndActivated(boolean dndActivated) {
    this.dndActivated = dndActivated;
  }
}
