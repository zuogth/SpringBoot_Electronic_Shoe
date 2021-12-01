package com.dth.spring_boot_shoe.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;

@Data
public class ApiException {
    private final String message;
    private final HttpStatus httpStatus;
    private final LocalDate date;

}
