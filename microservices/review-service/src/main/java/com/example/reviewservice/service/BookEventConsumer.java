package com.example.reviewservice.service;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.bookservice.model.BookCreatedEvent;

import java.util.function.Consumer;


@Configuration
public class BookEventConsumer
{

    @Bean
    public Consumer<BookCreatedEvent> bookCreatedConsumer()
    {
        return event ->
        {
            System.out.println("📘 Received book event: " + event);
            // create default reviews, indexes, etc
        };
    }
}
