package com.example.hyupup_tool.exception.client;

public class UnauthorizedException extends ClientException{

    protected UnauthorizedException(String message,String status){super(message,status);}

    public UnauthorizedException(String message){
        this(message,"401");
    }

}
