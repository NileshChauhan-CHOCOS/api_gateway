package com.manazeu.gateway.routes;

import jakarta.annotation.Nonnull;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
@SuppressWarnings("unused")
public interface RouteHandler {
    
    void configureRoute(@Nonnull RouteLocatorBuilder.Builder routes, @Nonnull RouteChain routeChain);
    
    //void configureRoute(@Nonnull RouteLocator routeLocator, @Nonnull RouteChain routeChain);
}
