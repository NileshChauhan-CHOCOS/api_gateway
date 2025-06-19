package com.manazeu.gateway.persistence.repo;

import com.manazeu.gateway.persistence.entity.mongo.RouteGroup;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface RouteGroupRepository extends ReactiveMongoRepository<RouteGroup,String> {
    
    @Query("{$and : [{'service_id' : ?0}, {'enabled' : ?1}]}")
    Flux<RouteGroup> findByServiceIdAndEnabled(Long serviceId, Boolean enabled);
}
