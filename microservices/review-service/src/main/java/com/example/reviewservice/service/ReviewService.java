package com.example.reviewservice.service;

import org.springframework.stereotype.Service;

import com.example.bookservice.model.Book;
import com.example.reviewservice.client.ReviewClient;

@Service
public class ReviewService
{
    private final ReviewClient reviewClient;

    public ReviewService(ReviewClient reviewClient) {
        this.reviewClient = reviewClient;
    }

    public Book getBookDetails(Long bookId) {
        return reviewClient.getBook(bookId);
    }
}



