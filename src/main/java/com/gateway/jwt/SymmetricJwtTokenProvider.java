package com.gateway.jwt;

import com.gateway.authentication.DefaultTestAuthentication;
import com.gateway.jwt.wrapper.TokenWrapper;
import com.gateway.jwt.enums.JWTTokenType;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import javax.crypto.spec.SecretKeySpec;
import java.util.*;

@Service
public class SymmetricJwtTokenProvider implements TokenProvider {
    
    private static final Logger Log = LoggerFactory.getLogger(SymmetricJwtTokenProvider.class);
    
    public static final String GRANT = "grant";
    
    public static final String ACCESS_GRANT = "ACC";
    
    public static final String ISS = "https://example.com"; // set as per your domain
    
    private final SecretKeySpec secretKeySpec;
    
    private final InMemoryTestUserRegistry testUserRegistry;
    
    public SymmetricJwtTokenProvider(SecretKeySpec secretKeySpec, InMemoryTestUserRegistry testUserRegistry){
        this.secretKeySpec = secretKeySpec;
        this.testUserRegistry = testUserRegistry;
    }
    
    /*
    Update token verification as per your requirement
     */
    public Mono<Boolean> verifyAccessToken(String token) throws ExpiredJwtException {
        if (token == null || token.isBlank()){
            return Mono.just(false);
        }
        Jws<Claims> claims = Jwts.parser()
                .verifyWith(secretKeySpec)
                .build().parseSignedClaims(token);
        JwsHeader headers = claims.getHeader();
        var grantType = headers.get(GRANT);
        if (!(grantType instanceof String) || !grantType.equals(ACCESS_GRANT)) {
            return Mono.just(false);
        }
        String issuer = claims.getPayload().getIssuer();
        if (!issuer.equals(ISS)) {
            return Mono.just(false);
        }
        long tokenExpiry = claims.getPayload().getExpiration().getTime();
        return Mono.just(tokenExpiry >= System.currentTimeMillis());
    }
    
    /**
     *
     * @param token : A JWT token
     * @return A Mono emits Authentication Object of the authenticated JWT Token
     *<p><strong>Warning: Remove the mentioned block of code from the below method</strong></p>
     * <pre>{@code
     *  if (token == null || token.isBlank()) {
     *      Log.info("token is null or blank authenticating as default test authentication");
     *      DefaultTestAuthentication authentication = new DefaultTestAuthentication(defaultTestAuthorities());
     *      return Mono.just(authentication);
     *  }
     *  }</pre>
     *  Replace with
     *  <pre>{@code
     *    if (token == null || token.isBlank()) {
     *      Log.info("token is null or blank");
     *      return Mono.empty();
     *     }
     *  }</pre>
     *
     * <p>
     * This logic would authenticate <strong>all requests without a JWT token</strong> using a default test authentication.
     * This is a security risk in production environments as it allows unauthenticated access.
     * </p>
     *  <strong>This is intended solely for routing tests and local development.</strong>
     *  It allows unauthenticated requests to proceed, which is a critical security risk
     *  in production environments.
     *  </p>
     *  <p>
     *  <strong>Important:</strong> This bypass must be removed before deploying to production,
     *  ensuring that all routes are properly protected by JWT-based authentication.
     *<p>
     */
    
    @Override
    public Mono<Authentication> getAuthentication(String token) {
        if (token == null || token.isBlank()){
            /* replace with return Mono.empty(); */
            Log.info("token is null or blank authenticating as default test authentication");
            DefaultTestAuthentication authentication = new DefaultTestAuthentication(defaultTestAuthorities());
            return Mono.just(authentication);
        }
        try {
            String subject = Jwts.parser()
                    .verifyWith(secretKeySpec)
                    .build().parseSignedClaims(token).getPayload().getSubject();
            if (subject == null) {
                return Mono.empty();
            }
            Authentication authentication = new UsernamePasswordAuthenticationToken(subject,token, Collections.emptySet());
            return Mono.just(authentication);
        } catch (Exception e) {
            Log.error("Error extracting userid from token : {} : {} ", token, Arrays.toString(e.getStackTrace()));
        }
        return Mono.empty();
    }
    
    public Mono<Authentication> getAuthenticationV(String token) {
        return Mono.fromCallable(() -> {
                    String subject = Jwts.parser()
                            .verifyWith(secretKeySpec)
                            .build()
                            .parseSignedClaims(token)
                            .getPayload()
                            .getSubject();
                    if (subject == null) {
                        return null;
                    }
                    return new UsernamePasswordAuthenticationToken(subject, token, Collections.emptySet());
                })
                .map(Authentication.class::cast)
                .onErrorResume(e -> {
                    Log.error("Exception parsing JWT token for token : {} ==> ",token, e);
                    return Mono.empty();
                });
    }
    
    private Set<GrantedAuthority> defaultTestAuthorities(){
        return Collections.singleton(
                new SimpleGrantedAuthority("Test")
        );
    }
    
    @Override
    public Mono<TokenWrapper> testToken(Integer id) {
        return testUserRegistry.user(id)
                .map(this::generateTestToken)
                .doOnError(e-> Log.error("Error generating test token"));
    }
    
    public TokenWrapper generateTestToken(UserDetails user){
        try {
            String token = Jwts.builder()
                    .subject(user.getUsername())
                    .id(UUID.randomUUID().toString())
                    .header().add("Test", JWTTokenType.TEST)
                    .and()
                    .issuer(ISS)
                    .issuedAt(new Date(System.currentTimeMillis()))
                    .expiration(new Date(System.currentTimeMillis() + 3600000L))
                    .signWith(secretKeySpec)
                    .compact();
            return new TokenWrapper(token);
        } catch (Exception e) {
            Log.error("Exception while generating access token for : {} ==> ", user, e);
        }
        return new TokenWrapper("");
    }
}
