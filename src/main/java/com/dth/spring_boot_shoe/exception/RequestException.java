package com.dth.spring_boot_shoe.exception;

import lombok.Getter;
import lombok.Setter;

public class RequestException extends RuntimeException{

    @Setter
    @Getter
    private String redirect;
    public RequestException(String message,String redirect) {
        super(message);
        this.redirect=redirect;
    }
}
