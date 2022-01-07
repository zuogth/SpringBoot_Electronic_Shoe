package com.dth.spring_boot_shoe.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class ApiHandleException {

    @ExceptionHandler(value = ApiRequestException.class)
    public ResponseEntity<Object> handleApiRequest(ApiRequestException e){
        HttpStatus httpStatus=HttpStatus.BAD_REQUEST;
        ApiException apiException=new ApiException(e.getMessage(),httpStatus, LocalDate.now());
        return new ResponseEntity<>(apiException,httpStatus);
    }



    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<ExceptionResponse> handleValidationExceptions(MethodArgumentNotValidException e){
        List<ExceptionResponse> responses=new ArrayList<>();
        e.getBindingResult().getAllErrors().forEach(err->{
            String fieldName=((FieldError)err).getField();
            String message=err.getDefaultMessage();
            responses.add(new ExceptionResponse(fieldName,message));
        });
        return responses;
    }
}
