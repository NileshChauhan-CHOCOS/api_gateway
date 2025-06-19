package com.manazeu.gateway.routes.farmology;

import com.manazeu.gateway.routes.RouteChain;
import com.manazeu.gateway.routes.RouteHandler;
import jakarta.annotation.Nonnull;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Component
@SuppressWarnings("unused")
public class FarmologyRouteHandler implements RouteHandler {
    
    @Override
    public void configureRoute(@Nonnull RouteLocatorBuilder.Builder routes, @Nonnull RouteChain routeChain) {
        routes.route("farmology_route",
                        r -> r.path("/novr/rs/**")
                                .and()
                                .method(HttpMethod.GET,HttpMethod.POST,HttpMethod.PUT,HttpMethod.PATCH,HttpMethod.DELETE)
                                .uri("http://localhost:5090")
                );
        routeChain.nextRoute();
    }
}
