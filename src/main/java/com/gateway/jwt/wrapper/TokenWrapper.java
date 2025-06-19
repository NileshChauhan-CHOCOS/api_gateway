package com.gateway.jwt.wrapper;

import java.util.Objects;

public record TokenWrapper(String token) {
    
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof TokenWrapper(String t))) return false;
        return Objects.equals(token, t);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(token);
    }
    
    @Override
    public String toString() {
        return token;
    }
}
