package com.gateway.exception.response;

import org.springframework.http.HttpStatus;

public class ExResponseDelegation extends ExceptionResponseDelegation{
    
    public ExResponseDelegation(String message, HttpStatus httpStatus){
        super(message, httpStatus);
    }
    
    public ExResponseDelegation(Throwable throwable){
        super(throwable, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
