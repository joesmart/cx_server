package com.server.cx.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class SystemException extends RuntimeException {

    public SystemException(){
        super();
    }

    public SystemException(String message){
        super(message);
    }

    public SystemException(Throwable e){
        super(e);
    }

    public SystemException(String message,Throwable e){
        super(message,e);
    }

}
