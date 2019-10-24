package io.pivotal.gatewaydemo.config;

import io.pivotal.gatewaydemo.filter.TokenFilter;
import org.springframework.cloud.gateway.filter.factory.AddRequestHeaderGatewayFilterFactory;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteConfig {

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("hello", r -> r.path("/hello")
                        .uri("http://localhost:8080/")
                        .filter(new TokenFilter()))
                .build();
    }
}
