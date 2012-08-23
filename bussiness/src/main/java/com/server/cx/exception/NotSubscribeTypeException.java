package com.server.cx.exception;


public class NotSubscribeTypeException extends SystemException {
    private static final long serialVersionUID = 1L;

    
    public NotSubscribeTypeException() {
    }

    public NotSubscribeTypeException(String message) {
        super(message);
    }

    public NotSubscribeTypeException(Throwable cause) {
        super(cause);
    }

}
