package com.gateway.authentication;

import io.jsonwebtoken.lang.Assert;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import java.io.Serial;
import java.io.Serializable;
import java.util.Set;

public class DefaultTestAuthentication extends AbstractAuthenticationToken implements Serializable {
    
    @Serial
    private static final long serialVersionUID = -906279457426018003L;
    
    private final Object principle;
    
    private Object credentials;
    
    /**
     * Creates a token with the supplied array of authorities.
     *
     * @param defaultTestAuthorities the collection of <tt>GrantedAuthority</tt>s for the principal
     *                    represented by this authentication object.
     */
    public DefaultTestAuthentication(Set<GrantedAuthority> defaultTestAuthorities) {
        super(defaultTestAuthorities);
        this.principle = "Tester@123";
        this.credentials = "credential";
        super.setAuthenticated(true);
    }
    
    @Override
    public String getName() {
        return "";
    }
    
    @Override
    public Object getCredentials() {
        return credentials;
    }
    
    @Override
    public Object getPrincipal() {
        return principle;
    }
    
    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        Assert.isTrue(!isAuthenticated,
                "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        super.setAuthenticated(false);
    }
    
    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
        this.credentials = null;
    }
    
    @Override
    public String toString() {
        return "{ principle : " + principle +
                ", Credential : " + credentials + " }";
                
    }
}
