package com.luis.helpdesk.services.exceptions;

public class ObjectNotFoundException extends RuntimeException{

    public ObjectNotFoundException(String msg, Throwable cause){
        super(msg, cause);
    }

    public ObjectNotFoundException(String msg){
        super(msg);
    }
}
