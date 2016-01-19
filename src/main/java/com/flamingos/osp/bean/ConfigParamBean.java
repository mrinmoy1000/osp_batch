package com.flamingos.osp.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.flamingos.osp.dto.ConfigParamDto;
import com.flamingos.osp.exception.OSPBusinessException;
import com.flamingos.osp.exception.OSPErrorHandler;
import com.flamingos.osp.service.ConfigParamLoaderService;
import com.flamingos.osp.util.AppConstants;
import com.flamingos.osp.util.AppUtil;

public class ConfigParamBean {
	
	@Autowired
	private ConfigParamLoaderService configParamLoaderService;
	@Autowired
	private OSPErrorHandler ospErrorHandler;
	private Map<String,ConfigParamDto> mapByParamId=new HashMap<String,ConfigParamDto>();
	private List<ConfigParamDto> listParam=new ArrayList<ConfigParamDto>();
	private Map<String,ConfigParamDto> mapByParamCodeAndName=new HashMap<String,ConfigParamDto>();
	private Map<String,List<ConfigParamDto>> mapByParamCode=new HashMap<String,List<ConfigParamDto>>();
	
	public void loadConfigParam(){
		Map<String,String> logMap=new HashMap<String,String>();
		logMap.put("Message", "Config Param Loading Started");
		AppUtil.writeToLog(AppConstants.CONFIG_LOADING_MODULE, AppConstants.LOG_TYPE_INFO, logMap);
		try{
		listParam=configParamLoaderService.loadConfigParam();
		for(ConfigParamDto param:listParam){
			mapByParamId.put(Integer.toString(param.getParameterid()), param);
			mapByParamCodeAndName.put(param.getCode()+"__"+param.getName(), param);
			List<ConfigParamDto> lstCode=mapByParamCode.get(param.getCode());
			if(null==lstCode){
				lstCode=new ArrayList<ConfigParamDto>();
				mapByParamCode.put(param.getCode(), lstCode);
			}
			lstCode.add(param);
		}
		}catch(OSPBusinessException ospEx){
			if("".equalsIgnoreCase(ospEx.getModuleName())){
				ospEx.setModuleName(AppConstants.CONFIG_LOADING_MODULE);
			}
			ospErrorHandler.handleOspBusinessException(ospEx);
			
		}catch(Exception gne){
			ospErrorHandler.handleGenericException(AppConstants.CONFIG_LOADING_MODULE, gne);
		}
		
	}
	
	public List<ConfigParamDto> getParametersByCode(String code){
		return mapByParamCode.get(code);
	}
	
	
	public ConfigParamDto getParameterById(String parameterId){
		return getMapByParamId().get(parameterId);
	}
	
	public ConfigParamDto getParameterByCodeName(String paramCode,String paramName){
		String key=paramCode+"__" + paramName;
		return getMapByParamCodeAndName().get(key);
	}

	public Map<String, ConfigParamDto> getMapByParamId() {
		return mapByParamId;
	}

	public List<ConfigParamDto> getListParam() {
		return listParam;
	}

	public Map<String, ConfigParamDto> getMapByParamCodeAndName() {
		return mapByParamCodeAndName;
	}

	public Map<String, List<ConfigParamDto>> getMapByParamCode() {
		return mapByParamCode;
	}
	

}
