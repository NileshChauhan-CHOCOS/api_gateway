package com.manazeu.gateway.persistence.repo;

import com.manazeu.gateway.persistence.entity.mongo.APIRoute;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
public interface RouteRepository extends ReactiveMongoRepository<APIRoute,String> {
    
    @Query(value = "{$and : [{'group_id' : {$in: [?0]}}, {'enabled' : ?1}]}")
    Flux<APIRoute> findByGroupIdInAndEnabled(List<Long> groupIds, Boolean enabled);
    
    @Query(value = "{$and : [{'path' : ?0}, {'enabled' : true}]}")
    Flux<APIRoute> findByPathAndEnabledTrue(String path);
    
    @Query(value = "{$and : [{'hash' : ?0}, {'enabled' : true}]}")
    Mono<APIRoute> findByHashAndEnabledTrue(String hash);
}
