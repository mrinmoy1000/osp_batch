package com.flamingos.osp.dto;

public class ConfigParamDto {

  private int parameterid;
  private String code;
  private String name;
  private String value;
  private String description;

  public int getParameterid() {
    return parameterid;
  }

  public void setParameterid(int parameterid) {
    this.parameterid = parameterid;
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
