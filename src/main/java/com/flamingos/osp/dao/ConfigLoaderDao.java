package com.flamingos.osp.dao;

import java.util.List;

import com.flamingos.osp.dto.ConfigParamDto;

public interface ConfigLoaderDao {
  public List<ConfigParamDto> loadConfigParam() throws Exception;

}
