package com.dth.spring_boot_shoe.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class ExceptionResponse {
    private String key;
    private String message;
}
