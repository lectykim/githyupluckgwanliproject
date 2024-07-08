package com.example.hyupup_tool.exception.client;

import com.example.hyupup_tool.exception.ApplicationException;

public abstract class ClientException extends ApplicationException {
    protected ClientException(String message,String status){
        super(message,status);
    }
}
