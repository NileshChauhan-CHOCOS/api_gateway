package com.manazeu.gateway.routes.docker;

import com.manazeu.gateway.routes.RouteChain;
import com.manazeu.gateway.routes.RouteHandler;
import jakarta.annotation.Nonnull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.builder.GatewayFilterSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.gateway.route.builder.UriSpec;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@SuppressWarnings("unused")
public class DockerRouteHandler implements RouteHandler {
    
    @Value("${service-spec.service.docker-service.id}")
    private String id;
    
    private static final Long SERVICE_ID = 439889L;
    
    private static final Logger logger = LoggerFactory.getLogger(DockerRouteHandler.class);
    
    private static final String UNQ_ID = "DOCKER_ROUTE";
    
    private static final String CONTEXT_PATH = "/docker";
    
    private static final String EXTERNAL_CONTEXT_PATH = "/nova";
    
    private static final String CAPTURE_GROUP_REGEX = "/(?<segment>.*)";
    
    private static final String BACK_REFERENCE = "/${segment}";
    
    @Value("${service-spec.service.docker-service.uri}")
    private String serviceURI;
    
    private DockerRewritePathFilterGateway rewritePathFilterGateway;
    
    private static DockerRewritePathFilterGateway.Config config;
    
    private static final Map<String, String> rewritePath = new HashMap<>();
    
    @Override
    public void configureRoute(@Nonnull RouteLocatorBuilder.Builder routes, @Nonnull RouteChain routeChain) {
        routes.route(UNQ_ID,
                r -> r.path("/nova/**")
                        .and()
                        .method(HttpMethod.GET)
                        .filters(this::applyRewriteFilters)
                        .uri(serviceURI)
        );
        routeChain.nextRoute();
    }
    
    private UriSpec applyRewriteFilters(GatewayFilterSpec filterSpec) {
        return filterSpec.rewritePath("/eaklr", "/earka");
    }
    
}
