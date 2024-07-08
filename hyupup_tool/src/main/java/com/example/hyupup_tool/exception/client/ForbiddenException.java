package com.example.hyupup_tool.exception.client;

public class ForbiddenException extends ClientException{
    protected ForbiddenException(String message,String status){
        super(message,status);
    }

    public ForbiddenException(String message){
        this(message,"403");
    }
}
