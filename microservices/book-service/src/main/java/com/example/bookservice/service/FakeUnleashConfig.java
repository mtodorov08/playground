package com.example.bookservice.service;

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
                           @Value("${unleash.flags." + BookService.UNLEASH_FEATURE_FLAG_BOOK_SERVICE + ":true}") boolean bookServiceEnabled)
    {
        FakeUnleash unleash = new FakeUnleash();
        if (bookServiceEnabled)
        {
            unleash.enable(BookService.UNLEASH_FEATURE_FLAG_BOOK_SERVICE);
        }
        else
        {
            unleash.disable(BookService.UNLEASH_FEATURE_FLAG_BOOK_SERVICE);
        }
        return unleash;
    }
}


