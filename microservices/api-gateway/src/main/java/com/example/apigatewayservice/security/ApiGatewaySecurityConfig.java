package com.example.apigatewayservice.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;


@Configuration
public class ApiGatewaySecurityConfig
{

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http)
    {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeExchange(auth -> auth.pathMatchers("/actuator/**", "/eureka/**").permitAll()
                                               .anyExchange().authenticated())
            .httpBasic();

        return http.build();
    }
}