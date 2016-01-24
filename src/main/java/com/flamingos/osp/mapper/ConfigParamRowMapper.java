package com.flamingos.osp.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.flamingos.osp.dto.ConfigParamDto;

public class ConfigParamRowMapper implements RowMapper<ConfigParamDto> {

  @Override
  public ConfigParamDto mapRow(ResultSet rs, int rowNum) throws SQLException {
    ConfigParamDto param = null;
    if (rs != null) {
      param = new ConfigParamDto();
      param.setParameterid(rs.getInt("param_id"));
      param.setCode(rs.getString("param_code"));
      param.setName(rs.getString("param_name"));
      param.setValue(rs.getString("param_value"));
      param.setDescription(rs.getString("param_desc"));
    }
    return param;
  }

}
