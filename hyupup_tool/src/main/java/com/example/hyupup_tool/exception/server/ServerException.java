package com.example.hyupup_tool.exception.server;

import com.example.hyupup_tool.exception.ApplicationException;

public class ServerException extends ApplicationException {
    protected ServerException(String message,String status){
        super(message,status);
    }

    public ServerException(String message){
        this(message,"500");
    }
}
