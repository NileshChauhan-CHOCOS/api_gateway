package com.manazeu.gateway.jwt;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class InMemoryTestUserRegistry {
    
    private static final AtomicInteger atomicInteger = new AtomicInteger(0);
    
    private List<UserDetails> testUserBucket = new ArrayList<>();
    
    private Map<Integer, UserDetails> userMapping = new HashMap<>();
    
    public synchronized Mono<Integer> registerUser(String username, String password){
        int id = atomicInteger.getAndIncrement();
        UserDetails testUser = User
                .withUsername(username)
                .password(password)
                .roles("TESTER")
                .build();
        testUserBucket.add(testUser);
        userMapping.put(id, testUser);
        return Mono.just(id);
    }
    
    public Mono<UserDetails> user(Integer id){
        if (!userMapping.containsKey(id)){
            Mono.error(new UsernameNotFoundException(String.format("User with id %d not found ", id)));
        }
        return Mono.just(userMapping.get(id));
    }
}
