package com.server.cx.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SystemException extends RuntimeException {

  Logger log = LoggerFactory.getLogger("SYSTEM_EXCEPTION");
  Throwable exception = null;
  String localMessage;

  public SystemException() {
    super();
  }

  public SystemException(String message) {
    super(message);
    localMessage = message;
  }

  public SystemException(Throwable e) {
    super(e);
    exception = e;
    log.error("exception:", e);
  }

  private static final long serialVersionUID = 1L;

  public String getLocalMessage() {
    return localMessage;
  }

  public void setLocalMessage(String localMessage) {
    this.localMessage = localMessage;
  }


}
