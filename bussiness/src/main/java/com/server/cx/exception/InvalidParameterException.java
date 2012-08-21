package com.server.cx.exception;

import lombok.Data;

@Data
public class InvalidParameterException extends SystemException {

    public InvalidParameterException(){
        super();
    }

    public InvalidParameterException(String message){
        super(message);
    }

    public InvalidParameterException(Throwable e){
        super(e);
    }

    public InvalidParameterException(String message,Throwable e){
        super(message,e);
    }

}
