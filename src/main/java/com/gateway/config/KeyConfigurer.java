package com.gateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.spec.SecretKeySpec;

@Configuration
public class KeyConfigurer {
    
    @Value("${rsa.key.secret}")
    private String secretKey;
    
    private static final String HMAC = "HmacSHA256";
    
    @Bean
    public SecretKeySpec secretKeySpec(){
        return new SecretKeySpec(secretKey.getBytes(),HMAC);
    }
}
