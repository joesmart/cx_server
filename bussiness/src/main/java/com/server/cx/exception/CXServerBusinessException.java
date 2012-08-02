package com.server.cx.exception;


public class CXServerBusinessException extends SystemException {

    private static final long serialVersionUID = 8746274310732322552L;

    public CXServerBusinessException(Throwable e, String message) {
        super(e);
        this.localMessage = message;
    }

    public CXServerBusinessException(String message) {
        super(message);
    }

}
