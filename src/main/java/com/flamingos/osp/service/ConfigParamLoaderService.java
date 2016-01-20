package com.flamingos.osp.service;

import java.util.List;

import com.flamingos.osp.dto.ConfigParamDto;

public interface ConfigParamLoaderService {

  public List<ConfigParamDto> loadConfigParam() throws Exception;

}
