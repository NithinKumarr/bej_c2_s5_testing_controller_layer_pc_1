package com.example.C13_S3_demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT,reason = "Customer already exists")
public class CustomerAlreadyExistsException extends Exception{
    public CustomerAlreadyExistsException(){
        super();
    }
}
