package com.example.apigatewayservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import io.getunleash.FakeUnleash;
import io.getunleash.Unleash;

@Configuration
@Profile("local")
public class FakeUnleashConfig {

    @Bean
    public Unleash unleash(
                           @Value("${unleash.flags.book-service-enabled:true}") boolean bookServiceEnabled)
    {
        FakeUnleash unleash = new FakeUnleash();
        if (bookServiceEnabled)
        {
            unleash.enable("book-service-enabled");
        }
        else
        {
            unleash.disable("book-service-enabled");
        }
        return unleash;
    }
}


