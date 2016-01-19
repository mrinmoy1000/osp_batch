package com.flamingos.osp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.flamingos.osp.dao.ConfigLoaderDao;
import com.flamingos.osp.dto.ConfigParamDto;
import com.flamingos.osp.service.ConfigParamLoaderService;

public class ConfigParamLoaderServiceImpl implements ConfigParamLoaderService{
	
	@Autowired
	private ConfigLoaderDao configLoaderDao;

	@Override
	public List<ConfigParamDto> loadConfigParam() throws Exception {
		return getConfigLoaderDao().loadConfigParam();
	}

	public ConfigLoaderDao getConfigLoaderDao() {
		return configLoaderDao;
	}


}
