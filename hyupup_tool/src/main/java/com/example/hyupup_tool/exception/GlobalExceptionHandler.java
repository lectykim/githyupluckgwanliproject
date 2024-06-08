package com.example.hyupup_tool.exception;

import com.example.hyupup_tool.entity.dto.ControllerErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SignUpFailException.class)
    public ResponseEntity<ControllerErrorResponse> handleSignUpFailException(SignUpFailException ex){
        ControllerErrorResponse controllerErrorResponse  =new ControllerErrorResponse(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(controllerErrorResponse);
    }

    @ExceptionHandler(ModifyUserInfoFailException.class)
    public ResponseEntity<ControllerErrorResponse> handleModifyUserInfoFailException(ModifyUserInfoFailException ex){
        ControllerErrorResponse controllerErrorResponse = new ControllerErrorResponse(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(controllerErrorResponse);
    }

}
