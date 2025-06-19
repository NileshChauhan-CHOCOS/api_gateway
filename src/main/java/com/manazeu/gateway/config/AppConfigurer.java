package com.manazeu.gateway.config;

import com.manazeu.gateway.QuantumMesh;
import com.manazeu.gateway.jwt.InMemoryTestUserRegistry;
import com.manazeu.gateway.jwt.TokenProvider;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

@Configuration
@SuppressWarnings("unused")
public class AppConfigurer {
    
    private static final Logger Log = LoggerFactory.getLogger(AppConfigurer.class);
    
    private final TokenProvider tokenProvider;
    
    private final InMemoryTestUserRegistry userRegistry;
    
    public AppConfigurer(TokenProvider tokenProvider, InMemoryTestUserRegistry userRegistry){
        this.tokenProvider = tokenProvider;
        this.userRegistry = userRegistry;
    }
    @Bean
    public MapReactiveUserDetailsService userDetailsService() {
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("Melodi@INITY")
                .password("1232rea84")
                .roles("USER")
                .build();
        return new MapReactiveUserDetailsService(user);
    }
    
    @PostConstruct
    public void registerDefaultUser(){
        userRegistry.registerUser("Melodi@INITY", "1232rea84")
                .flatMap(tokenProvider::testToken)
                .doOnNext(tokenWrapper -> Log.info("\u001B[34mDefault testing token:\u001B[0m\u001B[42;1m{}\u001B[0m",tokenWrapper.token()))
                .subscribe();
    }
    
}
