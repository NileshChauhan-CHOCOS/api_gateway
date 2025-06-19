package com.manazeu.gateway.filter;

import com.manazeu.gateway.jwt.SymmetricJwtTokenProvider;
import com.manazeu.gateway.jwt.TokenProvider;
import io.jsonwebtoken.ExpiredJwtException;
import io.micrometer.common.util.StringUtils;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
public class BearerTokenFilter implements WebFilter {
    
    private static final Logger Log = LoggerFactory.getLogger(BearerTokenFilter.class);
    
    private static final String AUTHORIZATION_HEADER = "Authorization";
    
    private static final String START_WITH_BEARER = "Bearer ";
    
    private final TokenProvider tokenProvider;
    
    public BearerTokenFilter(SymmetricJwtTokenProvider symmetricJwtTokenProvider){
        this.tokenProvider = symmetricJwtTokenProvider;
    }
    
    @Override
    @NonNull
    public Mono<Void> filter(ServerWebExchange exchange, @NonNull WebFilterChain chain) {
        Log.info("Authenticating request with bearer token");
        ServerHttpRequest httpRequest = exchange.getRequest();
        String bearerToken = extractBearerTokenFromRequest(httpRequest);
        return doAuthenticate(exchange, chain, bearerToken)
                .onErrorResume(ExpiredJwtException.class, e -> {
                    Log.error("Token expired : {}", e.getMessage());
                    var response = exchange.getResponse();
                    forbidden(response);
                    return Mono.empty();
                });
    }
    
    private Mono<Void> doAuthenticate(ServerWebExchange exchange, WebFilterChain chain, String token){
        return tokenProvider.getAuthenticationV(token)
                .flatMap(auth -> {
            Log.info("authenticated principle {} ", auth);
            SecurityContext context = new SecurityContextImpl(auth);
            return chain.filter(exchange)
                    .contextWrite(ReactiveSecurityContextHolder.withSecurityContext(Mono.just(context)));
            }).onErrorResume(UsernameNotFoundException.class, ex-> chain.filter(exchange));
    }
    
    private String extractBearerTokenFromRequest(ServerHttpRequest request){
        if (request == null){
            return "";
        }
        String bearerToken = request.getHeaders().getFirst(AUTHORIZATION_HEADER);
        if (StringUtils.isNotBlank(bearerToken) && bearerToken.startsWith(START_WITH_BEARER)){
            return bearerToken.substring(7);
        }
        return "";
    }
    
    private void forbidden(org.springframework.http.server.reactive.ServerHttpResponse response){
        response.setStatusCode(HttpStatus.FORBIDDEN);
    }
}
