package com.dth.spring_boot_shoe.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDate;

@ControllerAdvice
public class ApiHandleExcetion {

    @ExceptionHandler(value = ApiRequestException.class)
    public ResponseEntity<Object> handleApiRequest(ApiRequestException e){
        HttpStatus httpStatus=HttpStatus.BAD_REQUEST;
        ApiException apiException=new ApiException(e.getMessage(),httpStatus, LocalDate.now());
        return new ResponseEntity<>(apiException,httpStatus);
    }
}
