package com.manazeu.gateway.routes;

@FunctionalInterface
public interface RouteChain {
    
    void nextRoute();
    
}
