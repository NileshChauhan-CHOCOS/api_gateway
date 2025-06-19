package com.gateway.exception.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpStatus;

import java.time.Instant;

public abstract class ExceptionResponseDelegation {

    @JsonProperty("error")
    private String message;
    
    @JsonProperty("status")
    private HttpStatus httpStatus;
    
    @JsonProperty("path")
    private String path;
    
    @JsonProperty("timestamp")
    private Instant timestamp;
    
    protected ExceptionResponseDelegation(String message, HttpStatus httpStatus){
        this.message = message;
        this.httpStatus = httpStatus;
    }
    
    protected ExceptionResponseDelegation(Throwable throwable, HttpStatus httpStatus){
        this.message = throwable.getMessage();
        this.httpStatus = httpStatus;
        this.timestamp = Instant.now();
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
    
    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }
    
    public String getPath() {
        return path;
    }
    
    public void setPath(String path) {
        this.path = path;
    }
    
    public Instant getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }
}
