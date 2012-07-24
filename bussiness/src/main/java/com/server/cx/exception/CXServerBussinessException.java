package com.server.cx.exception;



public class CXServerBussinessException extends SystemException {

  private static final long serialVersionUID = 8746274310732322552L;

  public CXServerBussinessException(Throwable e, String message) {
    super(e);
    this.localMessage = message;
  }

  public CXServerBussinessException(String message) {
    super(message);
  }

}
