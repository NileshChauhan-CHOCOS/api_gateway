package com.manazeu.gateway.routes.docker;

import com.manazeu.gateway.persistence.repo.RouteGroupRepository;
import com.manazeu.gateway.persistence.repo.RouteRepository;
import com.manazeu.gateway.persistence.repo.ServiceClientRepository;
import com.manazeu.gateway.persistence.entity.mongo.APIRoute;
import com.manazeu.gateway.persistence.entity.mongo.RouteGroup;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.Arrays;

@Component
public class DockerRewritePathFilterGateway extends AbstractGatewayFilterFactory<DockerRewritePathFilterGateway.Config> {
    
    private static final Logger logger = LoggerFactory.getLogger(DockerRewritePathFilterGateway.class);
    
    private final ServiceClientRepository serviceClientRepository;
    
    private final RouteGroupRepository routeGroupRepository;
    
    private final RouteRepository routeRepository;
    
    public DockerRewritePathFilterGateway(ServiceClientRepository serviceClientRepository,
                                          RouteGroupRepository routeGroupRepository,
                                          RouteRepository routeRepository){
        super(Config.class);
        this.serviceClientRepository = serviceClientRepository;
        this.routeGroupRepository = routeGroupRepository;
        this.routeRepository = routeRepository;
    }
    
    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            String id = config.getId();
            Flux<APIRoute> routeFlux = routes(id);
            return routeFlux.doOnNext(APIRoute -> {
                String newPath = exchange.getRequest().getURI().getPath().replace(APIRoute.getUpRoute(), APIRoute.getDownRoute());
                exchange.getAttributes().put(ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR, exchange.getRequest().mutate().path(newPath).build().getURI());
            }).then(chain.filter(exchange));
        };
    }
    
    public Flux<APIRoute> routes(String id){
        return serviceClientRepository.findByIdAndEnabled(new ObjectId(id), true)
                .flatMapMany(serviceClient ->
                        routeGroupRepository.findByServiceIdAndEnabled(serviceClient.getServiceId(), true)
                                .doOnError(error -> logger.error("Error finding groups for client service : {} -> {}", id, Arrays.toString(error.getStackTrace())))
                                .map(RouteGroup::getGroupId)
                                .collectList()
                                .flatMapMany(groupIds -> {
                                    if (groupIds.isEmpty()){
                                        return Flux.empty();
                                    }
                                    return routeRepository.findByGroupIdInAndEnabled(groupIds, true)
                                            .doOnError(error -> logger.error("Error finding routes for client service {} -> {}", id, Arrays.toString(error.getStackTrace())));
                                })
                )
                .doOnError(error -> logger.error("Error finding service client for id : {} -> {} ", id, Arrays.toString(error.getStackTrace())));
    }
    @Setter
    @Getter
    public static class Config{
        
        private String id;
        
        public String getId() {
            return id;
        }
        
        public void setId(String id) {
            this.id = id;
        }
    }
}
