package com.mateuszziomek.issuestracker.apigateway.filters;

import com.mateuszziomek.issuestracker.shared.infrastructure.security.SecurityHeaders;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class SecurityHeadersFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        exchange
                .getRequest()
                .mutate()
                .headers(headers -> {
                    headers.remove(SecurityHeaders.ISSUES_TRACKER_USER_ID);
                    headers.remove(SecurityHeaders.ISSUES_TRACKER_USER_ROLE);
                })
                .build();

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
