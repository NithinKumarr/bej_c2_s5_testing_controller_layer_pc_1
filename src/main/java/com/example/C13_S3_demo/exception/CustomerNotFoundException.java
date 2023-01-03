package com.example.C13_S3_demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND,reason = "Customer with this id does not exists")
public class CustomerNotFoundException extends Exception{
    public CustomerNotFoundException(){
        super();
    }
}
