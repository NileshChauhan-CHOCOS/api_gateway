package com.manazeu.gateway.config;

import com.manazeu.gateway.filter.BearerTokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
@SuppressWarnings("unused")
public class WebFluxSecurityConfigurer {
    
    
    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http,
                                                            BearerTokenFilter bearerTokenFilter) {
        http.csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchanges -> exchanges
                        .anyExchange().authenticated()
                )
                .addFilterBefore(bearerTokenFilter,SecurityWebFiltersOrder.AUTHENTICATION);
        return http.build();
    }
    
}
