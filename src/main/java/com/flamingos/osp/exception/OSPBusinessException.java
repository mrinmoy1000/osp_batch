package com.flamingos.osp.exception;

public class OSPBusinessException extends RuntimeException {

  private static final long serialVersionUID = -6709233979163953528L;
  private String moduleName;
  private String errorCode;
  private String errorDescription;
  private Exception ex;

  public OSPBusinessException(String moduleName, String errorCode, String errorDescription) {
    this.moduleName = moduleName;
    this.errorCode = errorCode;
    this.errorDescription = errorDescription;
  }

  public OSPBusinessException(String moduleName, String errorCode, String errorDescription,
      Exception ex) {
    this.moduleName = moduleName;
    this.errorCode = errorCode;
    this.errorDescription = errorDescription;
    this.ex = ex;
  }

  public StackTraceElement[] getStatckTrace() {
    return ex.getStackTrace();
  }

  public String getModuleName() {
    return moduleName;
  }

  public String getErrorCode() {
    return errorCode;
  }

  public String getErrorDescription() {
    return errorDescription;
  }

  public Exception getEx() {
    return ex;
  }

  public void setModuleName(String moduleName) {
    this.moduleName = moduleName;
  }

}
