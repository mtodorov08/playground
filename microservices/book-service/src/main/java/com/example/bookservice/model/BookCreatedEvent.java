package com.example.bookservice.model;


public record BookCreatedEvent(Long bookId,
                               String title)
{
}
