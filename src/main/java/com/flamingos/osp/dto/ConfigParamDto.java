package com.flamingos.osp.dto;

public class ConfigParamDto {
	
	private int parameter_id;
	private String code;
	private String name;
	private String value;
	private String description;
	public int getParameter_id() {
		return parameter_id;
	}
	public void setParameter_id(int parameter_id) {
		this.parameter_id = parameter_id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	

}

