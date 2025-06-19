package com.manazeu.gateway.persistence.entity.mongo;

import lombok.Data;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
@Document(collection = "routes")
public class APIRoute implements Serializable {
    
    @Serial
    private static final long serialVersionUID = -1L;
    
    @MongoId
    private String id;
    
    @Field(name = "route_id", targetType = FieldType.DOUBLE, order = 1)
    private Long routeId;
    
    @Field(name = "service_id", targetType = FieldType.DOUBLE, order = 2)
    private Long serviceId;
    
    @Field(name = "group_id", targetType = FieldType.DOUBLE, order = 3)
    private Long groupId;
    
    @Field(name = "uri", targetType = FieldType.STRING, order = 4)
    private String uri;
    
    @Field(name = "p_def", targetType = FieldType.ARRAY, order = 5)
    private List<PredicateDefinition> predicateDefinitions;
    
    @Field(name = "f_def", targetType = FieldType.ARRAY, order = 6)
    private List<FilterDefinition> filterDefinitions;
    
    // external
    @Field(name = "up_route", targetType = FieldType.STRING, order = 7)
    private String upRoute;
    // internal
    @Field(name = "down_route", targetType = FieldType.STRING, order = 8)
    private String downRoute;
    
    @Field(name = "enabled", targetType = FieldType.BOOLEAN,order = 9)
    private Boolean enabled;
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public Long getRouteId() {
        return routeId;
    }
    
    public void setRouteId(Long routeId) {
        this.routeId = routeId;
    }
    
    public Long getServiceId() {
        return serviceId;
    }
    
    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }
    
    public Long getGroupId() {
        return groupId;
    }
    
    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }
    
    public String getUri() {
        return uri;
    }
    
    public void setUri(String uri) {
        this.uri = uri;
    }
    
    public String getUpRoute() {
        return upRoute;
    }
    
    public void setUpRoute(String upRoute) {
        this.upRoute = upRoute;
    }
    
    public String getDownRoute() {
        return downRoute;
    }
    
    public void setDownRoute(String downRoute) {
        this.downRoute = downRoute;
    }
    
    public Boolean getEnabled() {
        return enabled;
    }
    
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
    
    public List<PredicateDefinition> getPredicateDefinitions() {
        return predicateDefinitions;
    }
    
    public void setPredicateDefinitions(List<PredicateDefinition> predicateDefinitions) {
        this.predicateDefinitions = predicateDefinitions;
    }
    
    public List<FilterDefinition> getFilterDefinitions() {
        return filterDefinitions;
    }
    
    public void setFilterDefinitions(List<FilterDefinition> filterDefinitions) {
        this.filterDefinitions = filterDefinitions;
    }
    
}
