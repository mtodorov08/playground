package com.example.reviewservice.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookservice.model.Book;
import com.example.reviewservice.service.ReviewService;


@RestController
@RequestMapping("/api/reviews")
public class ReviewController
{
    private final ReviewService service;

    public ReviewController(ReviewService service)
    {
        this.service = service;
    }


    @GetMapping("/book/{bookId}")
    public Book getBook(@PathVariable Long bookId)
    {
        return service.getBookDetails(bookId);
    }
}
