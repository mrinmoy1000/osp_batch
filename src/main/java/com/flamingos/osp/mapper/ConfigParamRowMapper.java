package com.flamingos.osp.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.flamingos.osp.dto.ConfigParamDto;

public class ConfigParamRowMapper implements RowMapper<ConfigParamDto>{

	@Override
	public ConfigParamDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		ConfigParamDto param=null;
		if(rs!=null){
			param=new ConfigParamDto();
			param.setParameter_id(rs.getInt("parameter_id"));
			param.setCode(rs.getString("code"));
			param.setName(rs.getString("name"));
			param.setValue(rs.getString("value"));
			param.setDescription(rs.getString("description"));
		}
		return param;
	}

}
