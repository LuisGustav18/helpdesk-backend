package com.luis.helpdesk.services.exceptions;

public class DataIntegrityViolationException extends RuntimeException{

    public DataIntegrityViolationException(String msg, Throwable cause){
        super(msg, cause);
    }

    public DataIntegrityViolationException(String msg){
        super(msg);
    }
}
