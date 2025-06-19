package com.gateway.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.gateway.exception.GlobalExceptionHandler;
import com.gateway.routes.APIRouteService;
import com.gateway.routes.DbDrivenRouteLocator;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SuppressWarnings("unused")
public class RouteConfiguration {
    
    
    @Bean
    public RouteLocator routeLocator(APIRouteService apiRouteService,
                                     RouteLocatorBuilder routeLocatorBuilder,
                                     GlobalExceptionHandler globalExceptionHandler){
        return new DbDrivenRouteLocator(apiRouteService, routeLocatorBuilder,globalExceptionHandler,objectMapper());
    }
    
    
    private String extractServiceContext(String path){
        return path.split("/")[1];
    }
    
    @Bean
    public ObjectMapper objectMapper(){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }
    
}
