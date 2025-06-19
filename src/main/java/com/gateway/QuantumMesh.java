package com.gateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
public class QuantumMesh {
	
	private static final Logger Log = LoggerFactory.getLogger(QuantumMesh.class);

	public static void main(String[] args) {
		SpringApplication.run(QuantumMesh.class, args);
	}
	@Bean
	public CommandLineRunner logRoutes(RouteLocator routeLocator) {
		return args -> routeLocator.getRoutes()
				.doOnNext(route -> Log.info("Registered route: {}  -> {}  ", route.getId(), route.getUri()))
				.subscribe();
	}
}
