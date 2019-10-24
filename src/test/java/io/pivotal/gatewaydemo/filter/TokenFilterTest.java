package io.pivotal.gatewaydemo.filter;

import org.junit.jupiter.api.Test;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.mock.web.server.MockServerWebExchange;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TokenFilterTest {

    @Test
    public void test_filter_success() {
        TokenFilter target = new TokenFilter();
        MockServerHttpRequest request = MockServerHttpRequest
                .get("/hello")
                .header("X-Token", "secret")
                .build();
        MockServerWebExchange exchange = MockServerWebExchange.from(request);
        GatewayFilterChain filterChain = mock(GatewayFilterChain.class);

        exchange.getResponse().setStatusCode(HttpStatus.OK);
        when(filterChain.filter(any(ServerWebExchange.class))).thenReturn(Mono.<Void>empty());

        target.filter(exchange, filterChain);

        assertEquals(HttpStatus.OK, exchange.getResponse().getStatusCode());
    }

    @Test
    public void test_filter_wrong_header_value() {
        TokenFilter target = new TokenFilter();
        MockServerHttpRequest request = MockServerHttpRequest
                .get("/hello")
                .header("X-Token", "not secret")
                .build();
        MockServerWebExchange exchange = MockServerWebExchange.from(request);
        GatewayFilterChain filterChain = mock(GatewayFilterChain.class);

        exchange.getResponse().setStatusCode(HttpStatus.OK);
        when(filterChain.filter(any(ServerWebExchange.class))).thenReturn(Mono.<Void>empty());

        target.filter(exchange, filterChain);

        assertEquals(HttpStatus.UNAUTHORIZED, exchange.getResponse().getStatusCode());
    }

    @Test
    public void test_filter_missing_header() {
        TokenFilter target = new TokenFilter();
        MockServerHttpRequest request = MockServerHttpRequest
                .get("/hello")
                .build();
        MockServerWebExchange exchange = MockServerWebExchange.from(request);
        GatewayFilterChain filterChain = mock(GatewayFilterChain.class);

        exchange.getResponse().setStatusCode(HttpStatus.OK);
        when(filterChain.filter(any(ServerWebExchange.class))).thenReturn(Mono.<Void>empty());

        target.filter(exchange, filterChain);

        assertEquals(HttpStatus.UNAUTHORIZED, exchange.getResponse().getStatusCode());
    }
}
