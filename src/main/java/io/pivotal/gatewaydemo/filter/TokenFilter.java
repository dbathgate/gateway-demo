package io.pivotal.gatewaydemo.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.GatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

public class TokenFilter implements GatewayFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        List<String> token = exchange.getRequest().getHeaders().getOrEmpty("X-Token");

        if (!token.stream().filter(t -> "secret".equals(t)).findAny().isPresent()) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        }

        return chain.filter(exchange);
    }
}
