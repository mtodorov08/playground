package com.example.bookservice.service;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import com.example.bookservice.model.BookCreatedEvent;

import java.util.function.Supplier;


@Configuration
public class BookEventPublisher
{
    private BookCreatedEvent lastEvent;

    public void publish(BookCreatedEvent event)
    {
        this.lastEvent = event;
    }


    @Bean
    public Supplier<Message<BookCreatedEvent>> bookCreatedSupplier()
    {
        return () ->
        {
            if (lastEvent == null)
            {
                return null;
            }
            var msg = MessageBuilder.withPayload(lastEvent).build();
            lastEvent = null;
            return msg;
        };
    }
}
