package com.server.cx.exception;

public class MoneyNotEnoughException extends SystemException {

    private static final long serialVersionUID = 1L;

    public MoneyNotEnoughException() {
    }

    public MoneyNotEnoughException(String message) {
        super(message);
    }

    public MoneyNotEnoughException(Throwable cause) {
        super(cause);
    }

    public MoneyNotEnoughException(String message, Throwable cause) {
        super(message, cause);
    }

}
