package com.manazeu.gateway.routes;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.manazeu.gateway.exception.GlobalExceptionHandler;
import com.manazeu.gateway.exception.MissingCookieException;
import com.manazeu.gateway.persistence.entity.mongo.APIRoute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.handler.AsyncPredicate;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.*;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class DbDrivenRouteLocator implements RouteLocator {
    
    private static final Logger Log = LoggerFactory.getLogger(DbDrivenRouteLocator.class);
    
    private final APIRouteService apiRouteService;
    
    private final RouteLocatorBuilder routeLocatorBuilder;
    
    private final GlobalExceptionHandler globalExceptionHandler;
    
    private final ObjectMapper mapper;
    
    public DbDrivenRouteLocator(APIRouteService apiRouteService,
                                RouteLocatorBuilder routeLocatorBuilder,
                                GlobalExceptionHandler exceptionHandler,
                                ObjectMapper mapper){
        this.apiRouteService = apiRouteService;
        this.routeLocatorBuilder = routeLocatorBuilder;
        this.globalExceptionHandler = exceptionHandler;
        this.mapper = mapper;
    }
    
    
    @Override
    public Flux<Route> getRoutes() {
        RouteLocatorBuilder.Builder routesBuilder = routeLocatorBuilder.routes();
        return apiRouteService.getAll()
                .map(apiRoute -> routesBuilder.route(String.valueOf(apiRoute.getId()),
                        predicateSpec -> setPredicateSpec(apiRoute, predicateSpec))
                )
                .collectList()
                .flatMapMany(builders -> routesBuilder.build()
                        .getRoutes());
    }
    
    private Buildable<Route> setPredicateSpec(APIRoute apiRoute, PredicateSpec predicateSpec) {
        BooleanSpec booleanSpec = predicateSpec.path(apiRoute.getUpRoute());
        AsyncPredicate<ServerWebExchange> asyncPredicate = applyAsyncPredicates(apiRoute.getPredicateDefinitions());
        booleanSpec = booleanSpec.and().asyncPredicate(asyncPredicate);
        return booleanSpec
                .filters(f-> f.rewritePath(apiRoute.getUpRoute(), apiRoute.getDownRoute())
                        .modifyResponseBody(
                                String.class,
                                String.class,
                                ((ServerWebExchange exchange, String responseBody) -> sanitizeResponseBody(responseBody,apiRoute.getUpRoute()))))
                .uri(apiRoute.getUri());
    }
    
    private Mono<String> sanitizeResponseBody(String originalBody, String upRoute){
        if (originalBody == null){
            return Mono.just("");
        }
        try{
            Map<String, Object> response = mapper.readValue(originalBody, new TypeReference<>(){});
            response.replace("path", upRoute);
            String modifiedBody = mapper.writeValueAsString(response);
            return Mono.just(modifiedBody);
        }catch (Exception e){
            Log.error("Exception modifying response body for path {} ", upRoute,e);
            return Mono.just(originalBody);
        }
    }
    
    private AsyncPredicate<ServerWebExchange> applyAsyncPredicates(List<PredicateDefinition> predicates){
        AsyncPredicate<ServerWebExchange> asyncPredicate = exchange -> Mono.just(true)
                .onErrorResume(ex-> globalExceptionHandler.handle(exchange, ex).then(Mono.just(false)));
        if (predicates == null || predicates.isEmpty()){
            return asyncPredicate;
        }
        for (PredicateDefinition pred : predicates) {
            switch (pred.getName()){
                case "Path":
                    String path = pred.getArgs().get("_genkey_0");
                    asyncPredicate = asyncPredicate.and(exchange -> {
                        String requestPath = exchange.getRequest().getPath().toString();
                        return Mono.just(requestPath.matches(path));
                    });
                    break;
                case "Cookie":
                    Set<String> expectedCookies = new HashSet<>(pred.getArgs().keySet());
                    asyncPredicate = cookiePredicate(expectedCookies,asyncPredicate);
                    break;
                case "Method":
                    Set<String> allowedMethods = new HashSet<>(pred.getArgs().keySet());
                    asyncPredicate = methodPredicate(allowedMethods, asyncPredicate);
                    break;
                default:
                    break;
            }
        }
        return asyncPredicate;
    }
    
    private AsyncPredicate<ServerWebExchange> cookiePredicate(Set<String> expectedCookies,AsyncPredicate<ServerWebExchange> asyncPredicate){
        return asyncPredicate
                .and(exchange -> {
                    Log.debug("Expected cookies for the path : {} are {}",exchange.getRequest().getPath().value(), String.join(", ", expectedCookies));
                    Set<String> requestCookies = exchange.getRequest().getHeaders().get("Cookie").stream()
                                    .map(s -> s.split("=")[0])
                                            .collect(Collectors.toSet());
                    if (!requestCookies.containsAll(expectedCookies)){
                        return Mono.error(new MissingCookieException("Missing one or more required cookies : " + expectedCookies));
                    }
                    return Mono.just(true);
                });
    }
    
    private AsyncPredicate<ServerWebExchange> methodPredicate(Set<String> allowedMethods, AsyncPredicate<ServerWebExchange> asyncPredicate){
        return asyncPredicate
                .and(exchange -> {
            Log.info("Allowed methods for the path : {}  {} ", exchange.getRequest().getPath().value(),String.join(", ", allowedMethods));
            HttpMethod requestMethod = exchange.getRequest().getMethod();
            return Mono.just(allowedMethods.contains(requestMethod.name()));
        });
    }
}
