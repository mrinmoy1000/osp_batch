/**
 * 
 */
package com.flamingos.tech.osp.batch.newsletter.writer;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.flamingos.osp.bean.ConfigParamBean;
import com.flamingos.osp.dto.ConfigParamDto;
import com.flamingos.osp.util.AppConstants;
import com.flamingos.tech.osp.batch.model.User;
import com.flamingos.tech.osp.batch.newsletter.model.CommJob;
import com.flamingos.tech.osp.batch.newsletter.model.CommJobTemplate;
import com.flamingos.tech.osp.batch.newsletter.model.CommTemplate;
import com.flamingos.tech.osp.batch.newsletter.model.UserCommunication;

/**
 * @author Mrinmoy
 *
 */
public class UserCommJobWriter implements ItemWriter<UserCommunication>,
		InitializingBean {

	private String threadName;

	@Autowired
	private ConfigParamBean configParamBean;

	ConfigParamDto oEmailChannel = null;
	ConfigParamDto oSmsChannel = null;

	/**
	 * @param threadName
	 *            the threadName to set
	 */
	public void setThreadName(String threadName) {
		this.threadName = threadName;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		oEmailChannel = configParamBean.getParameterByCodeName(
				AppConstants.PARAM_CODE_COMM_CHANNEL,
				AppConstants.PARAM_NAME_EMAIL);
		oSmsChannel = configParamBean.getParameterByCodeName(
				AppConstants.PARAM_CODE_COMM_CHANNEL,
				AppConstants.PARAM_NAME_SMS);
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
					CommTemplate oCommTemplate = oTemplate.getCommTemplate();
					CommJob oCommJob = oTemplate.getCommJob();
					System.out.println("Processing User type : "
							+ oUser.getUserType() + " ,UserId: "
							+ oUser.getId() + " , Template: "
							+ oCommTemplate.getTemplateId() + " , JobId : "
							+ oCommJob.getCommJobId() + " , Channel : "
							+ oCommTemplate.getCommChannelId());
					if (oCommTemplate.getCommChannelId() == oEmailChannel
							.getParameterid()) {
						// SEND EMAIL
						System.out.println("Sending Email for User type : "
								+ oUser.getUserType() + " ,UserId: "
								+ oUser.getId() + " , Job Id : "
								+ oCommJob.getCommJobId());
					} else if (oCommTemplate.getCommChannelId() == oSmsChannel
							.getParameterid()) {
						// SEND SMS
						System.out.println("Sending SMS for User type : "
								+ oUser.getUserType() + " ,UserId: "
								+ oUser.getId() + " , Job Id : "
								+ oCommJob.getCommJobId());
					}
				}
			}
		}
	}

}