package com.manazeu.gateway.exception;

public class MissingCookieException extends RuntimeException{
    
    public MissingCookieException(String message){
        super(message);
    }
    
    public MissingCookieException(String message, Throwable t){
        super(message, t);
    }
    
    public MissingCookieException(Throwable cause){
        super(cause);
    }
    
    public MissingCookieException(String message, Throwable cause,
                                  boolean enableSuppression,
                                  boolean writableStackTrace){
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
