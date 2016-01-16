/**
 * 
 */
package com.flamingos.tech.osp.batch.newsletter.writer;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.flamingos.tech.osp.batch.model.User;
import com.flamingos.tech.osp.batch.newsletter.model.CommJob;
import com.flamingos.tech.osp.batch.newsletter.model.CommJobTemplate;
import com.flamingos.tech.osp.batch.newsletter.model.CommTemplate;
import com.flamingos.tech.osp.batch.newsletter.model.UserCommunication;

/**
 * @author Mrinmoy
 *
 */
public class UserCommJobWritter implements ItemWriter<UserCommunication> {

	private String threadName;
	
	/**
	 * @param threadName the threadName to set
	 */
	public void setThreadName(String threadName) {
		this.threadName = threadName;
	}

	private NamedParameterJdbcTemplate oNPJdbcTemplate;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public void write(List<? extends UserCommunication> oCommunications)
			throws Exception {
		System.out.println("UserCommunication : " + oCommunications.size());
		for (UserCommunication oUserComm : oCommunications) {
			User oUser = oUserComm.getoUser();
			List<CommJobTemplate> templates = oUserComm.getLstCommJobTemplate();
			if (null != oUser && null != templates && !templates.isEmpty()) {
				for (CommJobTemplate oTemplate : templates) {
					CommTemplate oCommTemplate=oTemplate.getCommTemplate();
					CommJob oCommJob=oTemplate.getCommJob();
					System.out.println("Processing User type : "+ oUser.getUserType() + " ,UserId: " + oUser.getId() + " , Template: " + oCommTemplate.getTemplateId() + " , JobId : " + oCommJob.getCommJobId() + " , Channel : " + oCommTemplate.getCommChannelId());
					if(oCommTemplate.getCommChannelId()==20){
						//SEND EMAIL
						System.out.println("Sending Email for User type : "+ oUser.getUserType() + " ,UserId: " + oUser.getId() + " , Job Id : " + oCommJob.getCommJobId());
					}else if(oCommTemplate.getCommChannelId()==21){
						//SEND SMS
						System.out.println("Sending SMS for User type : "+ oUser.getUserType() + " ,UserId: " + oUser.getId() + " , Job Id : " + oCommJob.getCommJobId());
					}
				}
			}
		}
	}
}