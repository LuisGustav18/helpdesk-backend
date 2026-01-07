package com.luis.helpdesk.resources.exceptions;

import java.io.Serializable;

public class FieldMessage implements Serializable {

    private String fieldNme;
    private String message;

    public FieldMessage(){
        super();
    }

    public FieldMessage(String fieldNme, String message) {
        this.fieldNme = fieldNme;
        this.message = message;
    }

    public String getFieldNme() {
        return fieldNme;
    }

    public void setFieldNme(String fieldNme) {
        this.fieldNme = fieldNme;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
