package com.manazeu.gateway.jwt;

import com.manazeu.gateway.jwt.wrapper.TokenWrapper;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public interface TokenProvider {
    
    Mono<Boolean> verifyAccessToken(String token) throws ExpiredJwtException;
    
    Mono<Authentication> getAuthentication(String token);
    
    Mono<TokenWrapper> testToken(Integer id);
    
    Mono<Authentication> getAuthenticationV(String token);
    
}
