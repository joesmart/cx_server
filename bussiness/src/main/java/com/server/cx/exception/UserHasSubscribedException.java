package com.server.cx.exception;

public class UserHasSubscribedException extends SystemException {
    private static final long serialVersionUID = 1L;

    public UserHasSubscribedException() {
    }

    public UserHasSubscribedException(String message) {
        super(message);
    }

    public UserHasSubscribedException(Throwable e) {
        super(e);
    }

    public UserHasSubscribedException(String message, Throwable e) {
        super(message, e);
    }

}
