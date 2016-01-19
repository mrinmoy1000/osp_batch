package com.flamingos.osp.exception;

import java.util.HashMap;
import java.util.Map;

import com.flamingos.osp.util.AppConstants;
import com.flamingos.osp.util.AppUtil;

public class OSPErrorHandler {
	
	public void handleRestException(Exception e){
		//Use Apputil.writeToLog to write log
	}
	
	public void handleGenericException(String moduleName,Exception gne){
		//Use Apputil.writeToLog to write log
		//errorCode=<generic error code for App>
		//errorMsg=gne.getMessage()
	}
	public void handleOspBusinessException(OSPBusinessException ospEx){
		if(null!=ospEx){
			Map<String,Object> errorMap=new HashMap<String,Object>();
			errorMap.put("Error Code", ospEx.getErrorCode());
			errorMap.put("Error Description", ospEx.getErrorDescription());
			errorMap.put("StackTrace",ospEx.getStatckTrace());
			AppUtil.writeToLog(ospEx.getModuleName(), AppConstants.LOG_TYPE_ERROR, errorMap);
		}
		
	}

}
