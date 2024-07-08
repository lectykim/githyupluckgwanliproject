package com.example.hyupup_tool.exception;

public abstract class CommonException extends RuntimeException{
    private final String message;

    public CommonException(String message){
        this.message = message;
    }
}
