package com.manazeu.gateway.filter;

import com.manazeu.gateway.routes.APIRouteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

// Not Required
/*@Component
public class DbSupportedGatewayFilter implements GatewayFilter {
    
    private static final Logger logger = LoggerFactory.getLogger(DbSupportedGatewayFilter.class);
    
    private final APIRouteService apiRouteService;
    
    public DbSupportedGatewayFilter(APIRouteService apiRouteService){
        this.apiRouteService = apiRouteService;
    }
    
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest serverHttpRequest = exchange.getRequest();
        String path = serverHttpRequest.getPath().value();
        logger.info("request path : {}", path);
        String contextPath = extractServiceContext(path);
        logger.info("context path for the service : {} ", contextPath);
         return apiRouteService.getApiRoute(contextPath)
                .flatMap(apiRoute -> {
                    ServerHttpRequest mutatedRequest = serverHttpRequest.mutate().path(apiRoute.getDownRoute()).build();
                    ServerWebExchange mutatedWebExchange = exchange.mutate()
                            .request(mutatedRequest)
                            .build();
                    return chain.filter(mutatedWebExchange);
                });
    }
    
    private String extractServiceContext(String path){
        return path.split("/")[1];
    }
}*/
