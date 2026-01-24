package com.example.bookservice.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import io.getunleash.DefaultUnleash;
import io.getunleash.Unleash;

@Configuration
@Profile("!local")
public class UnleashConfig {

    @Bean
    public Unleash unleash(
                           @Value("${unleash.app-name}") String appName,
                           @Value("${unleash.instance-id}") String instanceId,
                           @Value("${unleash.api-url}") String unleashApiUrl,
                           @Value("${unleash.api-key:}") String apiKey)
    {
        return new DefaultUnleash(new io.getunleash.util.UnleashConfig.Builder()
                                                                                .appName(appName)
                                                                                .instanceId(instanceId)
                                                                                .unleashAPI(unleashApiUrl)
                                                                                .apiKey(apiKey)
                                                                                .build());
    }
}


