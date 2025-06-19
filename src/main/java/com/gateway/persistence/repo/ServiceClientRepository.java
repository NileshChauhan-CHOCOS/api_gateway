package com.gateway.persistence.repo;

import com.gateway.persistence.entity.mongo.ServiceClient;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ServiceClientRepository extends ReactiveMongoRepository<ServiceClient, ObjectId> {
    
    @Query(value = "{'_id' : ?0,'enabled' : ?1 }")
    Mono<ServiceClient> findByIdAndEnabled(ObjectId id, Boolean enabled);
}
