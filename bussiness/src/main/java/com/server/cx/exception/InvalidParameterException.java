package com.server.cx.exception;

public class InvalidParameterException extends SystemException {

    private static final long serialVersionUID = 7287621469405140017L;

    public InvalidParameterException(Throwable e,String message) {
        super(e);
        this.localMessage = message;
    }
    
    public InvalidParameterException(String message) {
        super(message);
    }
}
