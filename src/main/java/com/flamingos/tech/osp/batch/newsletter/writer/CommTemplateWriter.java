/**
 * 
 */
package com.flamingos.tech.osp.batch.newsletter.writer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.flamingos.tech.osp.batch.buffer.CommTemplateBuffer;
import com.flamingos.tech.osp.batch.newsletter.model.CommJob;
import com.flamingos.tech.osp.batch.newsletter.model.CommJobTemplate;
import com.flamingos.tech.osp.batch.newsletter.model.CommTemplate;

/**
 * @author Mrinmoy
 *
 */
public class CommTemplateWriter implements ItemWriter<CommJobTemplate> {
	@Autowired
	private NamedParameterJdbcTemplate oNPJdbcTemplate;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.batch.item.ItemWriter#write(java.util.List)
	 */
	public void write(List<? extends CommJobTemplate> oCommJobTemplates)
			throws Exception {
		/**
		 * Buffer communication templates indexed by user type,role_status.
		 */
		String SQL_SELECTIVE_ACTIVE_USER_STATUS = "select param_id from osp_parameter where param_code='USER_STATUS' and active_status=1";
		List<Integer> activeUserStatus = jdbcTemplate.queryForList(
				SQL_SELECTIVE_ACTIVE_USER_STATUS, Integer.class);

		for (CommJobTemplate oCommJobTemplate : oCommJobTemplates) {
			CommTemplate oCommTemplate = oCommJobTemplate.getCommTemplate();
			CommJob oCommJob = oCommJobTemplate.getCommJob();
			String jobTemplateKey = oCommJob.getTemplateId() + "_"
					+ oCommJob.getTemplateId();
			CommTemplateBuffer.getJobTemplateIdMap().put(jobTemplateKey,
					oCommJobTemplate);
			/*
			 * For Professional key is Sub Category id.. All represents all sub
			 * category
			 */
			if ((oCommJob.getTargetUser() == 23) // PROFESSIONAL
					|| (oCommJob.getTargetUser() == 25)) { // ALL
				if (!oCommJob.isTargetUserAllStatus()) {
					CommTemplateBuffer.getProfessionalJobStatusSet().addAll(
							oCommJob.getLstTargetUserStatus());
				} else {
					CommTemplateBuffer.getProfessionalJobStatusSet().addAll(
							activeUserStatus);
				}
				Map<Integer, List<CommJobTemplate>> profJobTemplates = CommTemplateBuffer
						.getTemplateForProfessionals();
				List<Integer> activeSubCat = null;
				if (oCommJob.isTargetUserAllCat()) {
					String SQL_ALL_ACTIVE_SUB_CAT = "select sc.sub_cat_id from osp_sub_category sc join osp_category oc on sc.cat_id=oc.cat_id"
							+ " and sc.active_status=1 and oc.active_status=1";
					activeSubCat = jdbcTemplate.queryForList(
							SQL_ALL_ACTIVE_SUB_CAT, Integer.class);
				} else if (!oCommJob.getLstTargetUserCatIds().isEmpty()) {
					StringBuffer inCatIds = new StringBuffer();
					for (Integer catId : oCommJob.getLstTargetUserCatIds()) {
						inCatIds.append(catId).append(',');
					}
					inCatIds.deleteCharAt(inCatIds.length() - 1);
					String SQL_SELECTIVE_ACTIVE_SUB_CAT = "select sc.sub_cat_id from osp_sub_category sc join osp_category oc on sc.cat_id=oc.cat_id"
							+ " and sc.active_status=1 and oc.active_status=1 and oc.cat_id in ("
							+ inCatIds + ")";
					activeSubCat = jdbcTemplate.queryForList(
							SQL_SELECTIVE_ACTIVE_SUB_CAT, Integer.class);
				}
				if (null != activeSubCat && !activeSubCat.isEmpty()) {
					if (!oCommJob.isTargetUserAllSubCat()
							&& !oCommJob.getLstTargetUserSubCatIds().isEmpty()) {
						activeSubCat.retainAll(oCommJob
								.getLstTargetUserSubCatIds());
					}
					if (!activeSubCat.isEmpty()) {
						for (Integer subCatId : activeSubCat) {
							List<CommJobTemplate> subCatList = profJobTemplates
									.get(subCatId);
							if (null == subCatList) {
								subCatList = new ArrayList<CommJobTemplate>();
								profJobTemplates.put(subCatId, subCatList);
							}
							subCatList.add(oCommJobTemplate);
						}
						CommTemplateBuffer.getProfessionalJobSubCatSet()
								.addAll(activeSubCat);
					}
				}
			}

			// Assuming Client can be only ALL. i.e. All types of clients will
			// receive all emails for category ALl and Client.
			if ((oCommJob.getTargetUser() == 24) // CLIENT
					|| (oCommJob.getTargetUser() == 25)) {// ALL
				if (!oCommJob.isTargetUserAllStatus()) {
					CommTemplateBuffer.getCientJobStatusSet().addAll(
							oCommJob.getLstTargetUserStatus());
				} else {
					CommTemplateBuffer.getCientJobStatusSet().addAll(
							activeUserStatus);
				}
				Map<String, List<CommJobTemplate>> clientJobTemplates = CommTemplateBuffer
						.getTemplateForClients();
				List<CommJobTemplate> allClientJobTempList = clientJobTemplates
						.get("ALL");
				if (null == allClientJobTempList) {
					allClientJobTempList = new ArrayList<CommJobTemplate>();
					clientJobTemplates.put("ALL", allClientJobTempList);
				}
				allClientJobTempList.add(oCommJobTemplate);
			}
		}
	}

}
