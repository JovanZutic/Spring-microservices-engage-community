package org.example.apigateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfig {

    @Bean
    public RouteLocator gatewayRouter(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("organization-service", r->r.path("/api/org/**").uri("lb://organization-service"))
                .route("volunteer-service", r->r.path("/api/volunteer/**").uri("lb://volunteer-service"))
                .route("event-service", r->r.path("/api/event/**").uri("lb://event-service"))
                .build();
    }
}
