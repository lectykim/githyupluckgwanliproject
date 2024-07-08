package com.example.hyupup_tool.exception;

import com.example.hyupup_tool.entity.dto.ControllerErrorResponse;
import com.example.hyupup_tool.exception.client.ClientException;
import com.example.hyupup_tool.exception.server.ServerException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CommonException.class)
    public ResponseEntity<ErrorResponse> handleCommonException(CommonException ex){
        return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST.toString(),ex.getMessage()),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ClientException.class)
    public ResponseEntity<ErrorResponse> handleClientException(ClientException ex){
        return new ResponseEntity<>(new ErrorResponse(ex.getStatus(), ex.getMessage()),toHttpStatus(ex.getStatus()));
    }

    @ExceptionHandler(ServerException.class)
    public ResponseEntity<ErrorResponse> handleServerException(ServerException ex){
        return new ResponseEntity<>(new ErrorResponse(ex.getStatus(),ex.getMessage()),toHttpStatus(ex.getStatus()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex){
        return new ResponseEntity<>(new ErrorResponse("500","Internal Server Error"),HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @AllArgsConstructor
    @Getter
    public static class ErrorResponse{
        private String status;
        private String message;
    }

    private HttpStatus toHttpStatus(String status){
        return HttpStatus.valueOf(Integer.parseInt(status));
    }
}
