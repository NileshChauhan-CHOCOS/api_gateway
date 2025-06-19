package com.manazeu.gateway.persistence.entity.mongo;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;
import java.io.Serial;
import java.io.Serializable;

@Data
@Document(collection = "route_groups")
public class RouteGroup implements Serializable {
    
    @Serial
    private static final long serialVersionUID = -2L;
    
    @MongoId
    private String id;
    
    @Field(name = "group_id", targetType = FieldType.DOUBLE, order = 1)
    private Long groupId;
    
    @Field(name = "service_id", targetType = FieldType.DOUBLE, order = 2)
    private Long serviceId;
    
    @Field(name = "up_grp_name", targetType = FieldType.STRING,order = 3)
    private String externalGroupName;
    
    @Field(name = "down_grp_name", targetType = FieldType.STRING,order = 4)
    private String internalGroupName;
    
    @Field(name = "enabled", targetType = FieldType.BOOLEAN, order = 5)
    private Boolean enabled;
    
    public Long getGroupId(){
        return groupId;
    }
}
