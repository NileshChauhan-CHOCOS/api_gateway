package com.manazeu.gateway.auth;

import com.manazeu.gateway.routes.RouteChain;
import com.manazeu.gateway.routes.RouteHandler;
import jakarta.annotation.Nonnull;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.stereotype.Component;

@Component
@SuppressWarnings("unused")
public class AuthRouteHandler implements RouteHandler {
    
    @Override
    public void configureRoute(@Nonnull RouteLocatorBuilder.Builder routes, @Nonnull RouteChain routeChain) {
        routes.route("pika_auth",
                r -> r.path("/auth/**")
                        .filters(gatewayFilterSpec ->
                                gatewayFilterSpec.rewritePath("/auth/(?<segment>.*)", "/pika-auth/${segment}")
                        )
                        .uri("http://127.0.0.1:8080")
        );
        routeChain.nextRoute();
    }
}
