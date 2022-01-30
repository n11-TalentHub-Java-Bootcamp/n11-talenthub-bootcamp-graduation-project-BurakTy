package com.buraktuysuz.n11.finalproject.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CustomerNotMatchException extends RuntimeException{

    public CustomerNotMatchException(String message){
        super(message);
    }
}
