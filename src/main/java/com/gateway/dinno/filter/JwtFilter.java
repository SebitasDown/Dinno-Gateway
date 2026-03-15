package com.gateway.dinno.filter;

import com.gateway.dinno.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JwtFilter extends AbstractGatewayFilterFactory<JwtFilter.Config> {

    @Autowired
    private JwtService jwtService;

    private static final List<String> PUBLIC_PATHS = List.of(
            "/api/auth/login",
            "/api/auth/register");

    public JwtFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(JwtFilter.Config config) {
        return ((exchange, chain) -> {
            String path = exchange.getRequest().getURI().getPath();

            if (PUBLIC_PATHS.contains(path)) {
                return chain.filter(exchange);
            }

            String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            String token = authHeader.substring(7);
            if (!jwtService.isTokenValid(token)) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            String userId = jwtService.getUserIdFromToken(token);
            return chain.filter(exchange.mutate()
                    .request(r -> r.header("X-User-Id", userId))
                    .build());
        });
    }

    public static class Config {
    }
}
