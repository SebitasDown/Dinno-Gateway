package com.gateway.dinno.config;

import com.gateway.dinno.filter.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public JwtFilter jwtFilter() {
        return new JwtFilter();
    }
}
