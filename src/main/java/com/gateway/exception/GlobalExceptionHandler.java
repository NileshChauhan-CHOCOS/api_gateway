package com.gateway.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gateway.exception.response.ExResponseDelegationInitiator;
import com.gateway.exception.response.ExceptionResponseDelegation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@SuppressWarnings("unused")
public class GlobalExceptionHandler implements ErrorWebExceptionHandler {
    
    private final ExResponseDelegationInitiator exResponseDelegationInitiator;
    
    private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    
    private final ObjectMapper objectMapper;
    
    public GlobalExceptionHandler(ExResponseDelegationInitiator exResponseDelegationInitiator,
                                  ObjectMapper objectMapper){
        this.exResponseDelegationInitiator = exResponseDelegationInitiator;
        this.objectMapper = objectMapper;
    }
    
    @Override
    public @NonNull Mono<Void> handle(@NonNull ServerWebExchange exchange, @NonNull Throwable throwable) {
        LOG.error("Exception caught", throwable);
        ExceptionResponseDelegation responseDelegation = exResponseDelegationInitiator.assignDelegation(throwable);
        exchange.getResponse().setStatusCode(HttpStatus.OK);
        try {
            byte[] bytes = objectMapper.writeValueAsBytes(responseDelegation);
            DataBuffer buffer = exchange.getResponse()
                    .bufferFactory()
                    .wrap(bytes);
            return exchange.getResponse().writeWith(Mono.just(buffer));
        }catch (JsonProcessingException e){
            LOG.error("Error parsing the json object");
            Mono.error(e);
            return Mono.empty();
        }
    }
    
}
