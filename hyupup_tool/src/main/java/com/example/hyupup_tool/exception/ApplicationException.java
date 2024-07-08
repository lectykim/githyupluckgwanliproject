package com.example.hyupup_tool.exception;

import lombok.Getter;

@Getter
public abstract class ApplicationException extends RuntimeException{

    private final String message;
    private final String status;


    protected ApplicationException(String message,String status){
        this.message = message;
        this.status = status;
    }

}
