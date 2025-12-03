package com.example.bookservice.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
public class SecurityConfig
{

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http)
        throws Exception
    {
        http.csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth.requestMatchers("/actuator/**").permitAll() // health, info etc
                                               .anyRequest().authenticated())
            .httpBasic(Customizer.withDefaults()); // simplest authentication

        return http.build();
    }
}
