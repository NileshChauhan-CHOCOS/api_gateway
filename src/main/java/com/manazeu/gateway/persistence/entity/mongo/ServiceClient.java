package com.manazeu.gateway.persistence.entity.mongo;

import lombok.Getter;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Document(collection = "service_clients")
public class ServiceClient implements Serializable {
    
    @Serial
    private static final long serialVersionUID = 3L;
    
    @MongoId
    private ObjectId id;
    
    @Field(name = "srvce_id", targetType = FieldType.DOUBLE, order = 1)
    private Long serviceId;
    
    @Field(name = "name", targetType = FieldType.STRING, order = 2)
    private String name;
    
    @Field(name = "path", targetType = FieldType.STRING, order = 3)
    private String path;
    
    @Field(name = "uri" ,targetType = FieldType.STRING, order = 4)
    private String uri;
    
    @Field(name = "enabled", targetType = FieldType.BOOLEAN, order = 5)
    private Boolean enabled;
    
    public ServiceClient() {
    }
    
    public ObjectId getId() {
        return id;
    }
    
    public void setId(ObjectId id) {
        this.id = id;
    }
    
    public Long getServiceId() {
        return serviceId;
    }
    
    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getPath() {
        return path;
    }
    
    public void setPath(String path) {
        this.path = path;
    }
    
    public String getUri() {
        return uri;
    }
    
    public void setUri(String uri) {
        this.uri = uri;
    }
    
    public Boolean getEnabled() {
        return enabled;
    }
    
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
    
    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof ServiceClient)) return false;
        final ServiceClient other = (ServiceClient) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$serviceId = this.getServiceId();
        final Object other$serviceId = other.getServiceId();
        if (this$serviceId == null ? other$serviceId != null : !this$serviceId.equals(other$serviceId)) return false;
        final Object this$name = this.getName();
        final Object other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
        final Object this$contextFilter = this.getPath();
        final Object other$contextFilter = other.getPath();
        if (this$contextFilter == null ? other$contextFilter != null : !this$contextFilter.equals(other$contextFilter))
            return false;
        final Object this$enabled = this.getEnabled();
        final Object other$enabled = other.getEnabled();
        if (this$enabled == null ? other$enabled != null : !this$enabled.equals(other$enabled)) return false;
        return true;
    }
    
    protected boolean canEqual(final Object other) {
        return other instanceof ServiceClient;
    }
    
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $serviceId = this.getServiceId();
        result = result * PRIME + ($serviceId == null ? 43 : $serviceId.hashCode());
        final Object $name = this.getName();
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
        final Object $contextFilter = this.getPath();
        result = result * PRIME + ($contextFilter == null ? 43 : $contextFilter.hashCode());
        final Object $enabled = this.getEnabled();
        result = result * PRIME + ($enabled == null ? 43 : $enabled.hashCode());
        return result;
    }
    
    public String toString() {
        return "ServiceClient(id=" + this.getId() + ", serviceId=" + this.getServiceId() + ", name=" + this.getName() + ", contextFilter=" + this.getPath() + ", enabled=" + this.getEnabled() + ")";
    }
}
