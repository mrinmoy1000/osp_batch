/**
 * 
 */
package com.flamingos.tech.osp.batch.newsletter.model;

import java.util.ArrayList;
import java.util.List;

import com.flamingos.tech.osp.batch.model.User;

/**
 * @author Mrinmoy
 *
 */
public class UserCommunication {
	private List<CommJobTemplate> lstCommJobTemplate=new ArrayList<CommJobTemplate>();
	private User oUser;
	
	/**
	 * @return the lstCommJobTemplate
	 */
	public List<CommJobTemplate> getLstCommJobTemplate() {
		return lstCommJobTemplate;
	}
	/**
	 * @param lstCommTemplate the lstCommJobTemplate to set
	 */
	public void setLstCommJobTemplate(List<CommJobTemplate> lstCommJobTemplate) {
		this.lstCommJobTemplate = lstCommJobTemplate;
	}
	/**
	 * @return the oUser
	 */
	public User getoUser() {
		return oUser;
	}
	/**
	 * @param oUser the oUser to set
	 */
	public void setoUser(User oUser) {
		this.oUser = oUser;
	}
}