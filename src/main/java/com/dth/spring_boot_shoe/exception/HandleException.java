package com.dth.spring_boot_shoe.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDate;

@ControllerAdvice
public class HandleException {
    @ExceptionHandler(value = RequestException.class)
    public String handleApiRequest(RequestException e, Model model){
        HttpStatus httpStatus=HttpStatus.BAD_REQUEST;
        ApiException exception=new ApiException(e.getMessage(),httpStatus, LocalDate.now());
        model.addAttribute("error",exception);
        return e.getRedirect();
    }
}
