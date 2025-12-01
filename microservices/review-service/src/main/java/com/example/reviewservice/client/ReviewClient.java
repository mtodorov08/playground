package com.example.reviewservice.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.bookservice.model.Book;


@FeignClient(name = "book-service")
public interface ReviewClient
{
    @GetMapping("/api/books/{id}")
    Book getBook(@PathVariable("id") Long id);
}
