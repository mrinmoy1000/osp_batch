package com.flamingos.osp.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;

import com.flamingos.osp.dao.ConfigLoaderDao;
import com.flamingos.osp.dto.ConfigParamDto;
import com.flamingos.osp.exception.OSPBusinessException;
import com.flamingos.osp.mapper.ConfigParamRowMapper;
import com.flamingos.osp.util.AppConstants;

public class ConfigLoaderDaoImpl extends BaseDaoImpl implements ConfigLoaderDao {

  @Value("${query_osp_parameter_select}")
  private String QUERY_OSP_PARAMETER_SELECT;

  @Override
  public List<ConfigParamDto> loadConfigParam() throws OSPBusinessException, Exception {
    List<ConfigParamDto> paramList = null;
    Object[] values = new Object[] {1};
    paramList =
        getJdbcTemplate().query(QUERY_OSP_PARAMETER_SELECT, values, new ConfigParamRowMapper());
    if (null != paramList) {
      if (!(paramList.size() > 0)) {
        throw new OSPBusinessException("", AppConstants.DB_NO_RECORD_FOUND_ERRCODE,
            AppConstants.DB_NO_RECORD_FOUND_ERRMSG);
      }
    } else {
      throw new OSPBusinessException("", AppConstants.DB_NO_RECORD_FOUND_ERRCODE,
          AppConstants.DB_NO_RECORD_FOUND_ERRMSG);
    }
    return paramList;
  }

}
