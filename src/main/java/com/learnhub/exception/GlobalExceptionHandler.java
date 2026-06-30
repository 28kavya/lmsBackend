package com.learnhub.exception;

import com.learnhub.response.ResponseApi;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResponseApi> handleException(ResourceNotFoundException ex){
        return  new ResponseEntity(ResponseApi.builder().data(null).message(ex.getMessage()).build(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseApi> handleException2(Exception ex){
        return  new ResponseEntity(ResponseApi.builder().data(null).message(ex.getMessage()).build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ResponseApi> handleException3(Exception ex){
        return  new ResponseEntity(ResponseApi.builder().data(null).message(ex.getMessage()).build(), HttpStatus.NOT_FOUND);
    }

}
