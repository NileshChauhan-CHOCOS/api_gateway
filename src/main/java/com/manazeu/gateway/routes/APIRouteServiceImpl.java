package com.manazeu.gateway.routes;

import com.manazeu.gateway.persistence.repo.RouteRepository;
import com.manazeu.gateway.persistence.entity.mongo.APIRoute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.atomic.AtomicLong;

@Service
public class APIRouteServiceImpl implements APIRouteService {
    
    private static final Logger logger = LoggerFactory.getLogger(APIRouteServiceImpl.class);
    
    private final RouteRepository routeRepository;
    
    public APIRouteServiceImpl(RouteRepository routeRepository){
        this.routeRepository = routeRepository;
    }
    
    @Override
    public Mono<APIRoute> create(APIRoute apiRoute) {
        logger.info("persisting the api route service id : {}  and group id : {}", apiRoute.getServiceId(), apiRoute.getGroupId());
       return  routeRepository.save(apiRoute)
                        .doOnNext(route ->
                                logger.info("route successfully persisted to the database for service : {}, group {} with route id : {}", route.getServiceId(), route.getGroupId(), route.getId()));
    }
    
    @Override
    public Mono<APIRoute> getApiRoute(String hash) {
        return null;
    }
    
    @Override
    public Flux<APIRoute> getByPath(String path) {
        AtomicLong counter = new AtomicLong();
        logger.info("retrieving the routes for the path {} ",path);
        return routeRepository.findByPathAndEnabledTrue(path)
                .doOnNext(r-> counter.incrementAndGet())
                .doOnComplete(()-> logger.info("Total route fetch for the path : {} is -> {}", path,counter));
    }
    
    @Override
    public Flux<APIRoute> getAll() {
        return routeRepository.findAll();
    }
    
    @Override
    public Mono<APIRoute> getById(String id) {
        logger.info("retrieving the route with id {} ", id);
        return routeRepository.findById(id)
                .doOnNext(apiRoute ->
                        logger.info("route retrieved for the id {} ", apiRoute.getRouteId()));
    }
}
