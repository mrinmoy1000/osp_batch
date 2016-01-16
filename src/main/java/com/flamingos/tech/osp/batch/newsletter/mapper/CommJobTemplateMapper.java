/**
 * 
 */
package com.flamingos.tech.osp.batch.newsletter.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.jdbc.core.RowMapper;

import com.flamingos.tech.osp.batch.newsletter.model.CommJob;
import com.flamingos.tech.osp.batch.newsletter.model.CommJobTemplate;
import com.flamingos.tech.osp.batch.newsletter.model.CommTemplate;

/**
 * @author Mrinmoy
 *
 */
public class CommJobTemplateMapper implements RowMapper<CommJobTemplate> {

	public CommJobTemplate mapRow(ResultSet rs, int rowNum) throws SQLException {
		CommJobTemplate commJobTemplate = new CommJobTemplate();
		CommTemplate commTemplate = new CommTemplate();
		CommJob commJob = new CommJob();
		commJobTemplate.setCommJob(commJob);
		commJobTemplate.setCommTemplate(commTemplate);

		commTemplate.setTemplateId(rs.getInt("comm_template_id"));
		commTemplate.setTemplateName(rs.getString("template_name"));
		commTemplate.setCommChannelId(rs.getInt("channel_id"));
		commTemplate.setTemplateCatId(rs.getInt("temp_cat_id"));
		commTemplate.setTemplateSubCatId(rs.getInt("temp_sub_cat_id"));
		commTemplate.setIsEditable(rs.getBoolean("is_editable"));
		commTemplate.setTemplateFileName(rs.getString("file_path"));

		commJob.setCommJobId(rs.getInt("comm_job_id"));
		commJob.setTemplateId(rs.getInt("comm_template_id"));
		commJob.setTargetUser(rs.getInt("target_user"));
		commJob.setTargetUserCat(rs.getString("target_user_cat"));
		commJob.setTargetUserSubCat(rs.getString("target_user_sub_cat"));
		commJob.setEmailSubject(rs.getString("email_subject"));
		commJob.setContent(rs.getBlob("content"));
		commJob.setImageUrl(rs.getString("image_url"));
		commJob.setJobDate(rs.getDate("job_date"));
		commJob.setJobStatus(rs.getInt("job_status"));;
		commJob.setImplicitJob(rs.getBoolean("is_implicit_job"));
		
		if (null != rs.getString("target_user_cat")
				&& !"".equals(rs.getString("target_user_cat").trim())) {
			if ("ALL".equalsIgnoreCase(rs.getString("target_user_cat"))) {
				commJob.setTargetUserAllCat(true);
			} else {
				String[] strIds = rs.getString("target_user_cat").split(":");
				for (int index = 0; index < strIds.length; index++) {
					commJob.getLstTargetUserCatIds().add(
							Integer.valueOf(strIds[index]));
				}
			}
		} else {
			commJob.setTargetUserAllCat(true);
		}
		
		if (null != rs.getString("target_user_sub_cat")
				&& !"".equals(rs.getString("target_user_sub_cat").trim())) {
			if ("ALL".equalsIgnoreCase(rs.getString("target_user_sub_cat"))) {
				commJob.setTargetUserAllSubCat(true);
			} else {
				String[] strIds = rs.getString("target_user_sub_cat")
						.split(":");
				for (int index = 0; index < strIds.length; index++) {
					commJob.getLstTargetUserSubCatIds().add(
							Integer.valueOf(strIds[index]));
				}
			}
		} else {
			commJob.setTargetUserAllSubCat(true);
		}
		if (null != rs.getString("target_user_status")
				&& !"".equals(rs.getString("target_user_status").trim())) {
			if ("ALL".equalsIgnoreCase(rs.getString("target_user_status"))) {
				commJob.setTargetUserAllStatus(true);
			} else {
				String[] strIds = rs.getString("target_user_status").split(":");
				for (int index = 0; index < strIds.length; index++) {
					commJob.getLstTargetUserStatus().add(
							Integer.valueOf(strIds[index]));
				}
			}
		} else {
			commJob.setTargetUserAllStatus(true);
		}
		return commJobTemplate;
	}
}
