package com.server.cx.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
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
