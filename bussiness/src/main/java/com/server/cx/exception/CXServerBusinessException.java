package com.server.cx.exception;


import lombok.Data;

@Data
public class CXServerBusinessException extends SystemException {
    public CXServerBusinessException(){
        super();
    }

    public CXServerBusinessException(String message){
        super(message);
    }

    public CXServerBusinessException(Throwable e){
        super(e);
    }

    public CXServerBusinessException(String message,Throwable e){
        super(message,e);
    }
}
