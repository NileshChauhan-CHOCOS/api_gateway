package com.manazeu.gateway.auth;

import com.manazeu.gateway.jwt.InMemoryTestUserRegistry;
import com.manazeu.gateway.jwt.TokenProvider;
import com.manazeu.gateway.util.GlobalLogger;
import org.bson.assertions.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.test.StepVerifier;

@SpringBootTest
class DefaultJwtTokenTest {
    
    private static final Logger Log = GlobalLogger.logger();
    
    @Autowired
    private TokenProvider tokenProvider;
    
    @Autowired
    private InMemoryTestUserRegistry userRegistry;
    
    @Test
    void shouldReturnDefaultTestToken(){
        StepVerifier
                .create(
                        userRegistry.registerUser("Meloditeam@INITY", "1232rea84")
                                .flatMap(id-> tokenProvider
                                        .testToken(id))
                )
                .assertNext(tokenWrapper -> {
                    Log.info("Your testing token : {} ", tokenWrapper.token());
                    Assertions.assertNotNull(tokenWrapper.token());
                })
                .verifyComplete();
    }
}
