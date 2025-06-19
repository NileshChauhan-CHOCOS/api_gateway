package com.gateway.exception.response;


import org.springframework.http.HttpStatus;

public class MissingCookieResponseDelegation extends ExceptionResponseDelegation{
    
    public MissingCookieResponseDelegation(Throwable throwable){
        super(throwable, HttpStatus.BAD_REQUEST);
    }
}
