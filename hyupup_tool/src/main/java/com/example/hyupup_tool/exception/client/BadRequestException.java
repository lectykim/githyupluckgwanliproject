package com.example.hyupup_tool.exception.client;

public class BadRequestException extends ClientException{
    protected BadRequestException(String message,String status){
        super(message,status);
    }

    public BadRequestException(String message){
        this(message,"400");
    }
}
