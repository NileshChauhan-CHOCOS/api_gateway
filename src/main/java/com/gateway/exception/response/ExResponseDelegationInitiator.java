package com.gateway.exception.response;

import com.gateway.exception.MissingCookieException;
import com.gateway.util.GlobalLogger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.net.ConnectException;

@Component
public class ExResponseDelegationInitiator {
    
    public ExceptionResponseDelegation assignDelegation(Throwable throwable){
        GlobalLogger.logger().info("Throwable is type of : {}", throwable.getClass());
        if (throwable instanceof MissingCookieException){
            GlobalLogger.logger().info("Throwable is instance of {} ", MissingCookieException.class);
            return new MissingCookieResponseDelegation(throwable);
        }
        else if (throwable instanceof ConnectException){
            return new ExResponseDelegation("Connection refused", HttpStatus.SERVICE_UNAVAILABLE);
        }
        else {
            return new ExResponseDelegation(throwable);
        }
    }
}
