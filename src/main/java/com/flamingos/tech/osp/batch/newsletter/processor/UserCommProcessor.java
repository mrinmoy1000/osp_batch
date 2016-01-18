/**
 * 
 */
package com.flamingos.tech.osp.batch.newsletter.processor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.batch.item.ItemProcessor;

import com.flamingos.tech.osp.batch.buffer.CommTemplateBuffer;
import com.flamingos.tech.osp.batch.model.User;
import com.flamingos.tech.osp.batch.newsletter.model.CommJob;
import com.flamingos.tech.osp.batch.newsletter.model.CommJobTemplate;
import com.flamingos.tech.osp.batch.newsletter.model.CommTemplate;
import com.flamingos.tech.osp.batch.newsletter.model.UserCommunication;

/**
 * @author Mrinmoy
 *
 */
public class UserCommProcessor implements
		ItemProcessor<User, UserCommunication> {

	private String threadName;

	/**
	 * @param threadName
	 *            the threadName to set
	 */
	public void setThreadName(String threadName) {
		this.threadName = threadName;
	}

	public UserCommunication process(User user) throws Exception {
		UserCommunication communication = null;
		/*
		 * Logic to find All applicable templates for the user. If no matching
		 * template found, return null..(i.e. skip the user)
		 */
		List<CommJobTemplate> applicabelTemplates = new ArrayList<CommJobTemplate>();
		List<CommJobTemplate> finalApplicabelTemplates = new ArrayList<CommJobTemplate>();
		if (user.getUserType() == 23) {
			Map<Integer, List<CommJobTemplate>> profTemplates = CommTemplateBuffer
					.getTemplateForProfessionals();
			/*
			 * if (null != profTemplates.get("ALL")) {// All Segment Templates.
			 * applicabelTemplates.addAll(profTemplates.get("ALL")); }
			 */
			if (null != profTemplates.get(user.getSubCategoryId())) {
				applicabelTemplates.addAll(profTemplates.get(user
						.getSubCategoryId()));
			}
		} else if (user.getUserType() == 24) {
			Map<String, List<CommJobTemplate>> clientTemplates = CommTemplateBuffer
					.getTemplateForClients();
			if (null != clientTemplates.get("ALL")) {// All Segment Templates.
				applicabelTemplates.addAll(clientTemplates.get("ALL"));
			}
			/*
			 * String key = user.getRoleId() + ""; if (null !=
			 * clientTemplates.get(key)) {
			 * applicabelTemplates.addAll(clientTemplates.get(key)); }
			 */
		}
		if (!applicabelTemplates.isEmpty()) {
			finalApplicabelTemplates = discardUnsubscribedAndRepeatTemplate(user,
					applicabelTemplates);
		}
		if (!finalApplicabelTemplates.isEmpty()) {
			communication=new UserCommunication();
			communication.setLstCommJobTemplate(finalApplicabelTemplates);
			communication.setoUser(user);
		}
		return communication;
	}

	private List<CommJobTemplate> discardUnsubscribedAndRepeatTemplate(
			User user, List<CommJobTemplate> applicabelTemplates) {
		// TODO.
		List<CommJobTemplate> finalApplicableTemplates = new ArrayList<CommJobTemplate>();
		for (CommJobTemplate oJobTemplate : applicabelTemplates) {
			CommJob oCommJob = oJobTemplate.getCommJob();
			CommTemplate oTemplate = oJobTemplate.getCommTemplate();
			if ((oCommJob.isTargetUserAllStatus() || oCommJob
					.getLstTargetUserStatus().contains(user.getStatus()))
					&& oCommJob.getLstTargetUserStatus().contains(
							user.getStatus())) {
				if (oTemplate.getCommChannelId() == 20
						&& user.getSubscriptionsCat().contains(
								oTemplate.getTemplateSubCatId())) {// fetch from
																	// parameter
																	// cache
					finalApplicableTemplates.add(oJobTemplate);
				} else if (oTemplate.getCommChannelId() == 21
						&& !user.isDndActivated()) {// fetch from parameter cache
					finalApplicableTemplates.add(oJobTemplate);
				}
			}
		}
		return finalApplicableTemplates;
	}

}
