/**
 * 
 */
package com.flamingos.tech.osp.batch.newsletter.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.flamingos.tech.osp.batch.model.User;

/**
 * @author Mrinmoy
 *
 */
public class UserMapper implements RowMapper<User> {

	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		User oUser=new User();
		oUser.setId(rs.getLong("id"));
		oUser.setUserType(rs.getInt("u_type"));
		oUser.setfName(rs.getString("f_name"));
		oUser.setmName(rs.getString("m_name"));
		oUser.setlName(rs.getString("l_name"));
		oUser.setPhoneNumber(rs.getString("contact_phone"));
		oUser.setEmailId(rs.getString("contact_email"));
		oUser.setStatus(rs.getInt("active_status"));
		if (null != rs.getString("subsc_ids")
				&& !"".equals(rs.getString("subsc_ids").trim())) {
				String[] strIds = rs.getString("subsc_ids").split(":");
				for (int index = 0; index < strIds.length; index++) {
					oUser.getSubscriptionsCat().add(
							Integer.valueOf(strIds[index]));
				}
		}
		oUser.setCategoryId(rs.getInt("cat_id"));
		oUser.setSubCategoryId(rs.getInt("sub_cat_id")); 
		oUser.setDndActivated(rs.getBoolean("dnd_activated"));
		return oUser;
	}

}
