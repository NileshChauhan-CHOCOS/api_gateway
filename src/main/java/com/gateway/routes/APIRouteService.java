package com.gateway.routes;

import com.gateway.persistence.entity.mongo.APIRoute;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface APIRouteService {
    
    Mono<APIRoute> create(APIRoute apiRoute);
    
    Mono<APIRoute> getApiRoute(String hash);
    
    Flux<APIRoute> getByPath(String path);

    Flux<APIRoute> getAll();
    
    Mono<APIRoute> getById(String id);
}
