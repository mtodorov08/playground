package com.example.bookservice.service;


import java.util.List;

import org.springframework.stereotype.Service;

import com.example.bookservice.model.Book;
import com.example.bookservice.model.BookCreatedEvent;
import com.example.bookservice.repository.BookRepository;

import io.getunleash.Unleash;


@Service
public class BookService
{
    public static final String UNLEASH_FEATURE_FLAG_BOOK_SERVICE = "book-service-enabled";

    private final BookRepository repo;
    private final BookEventPublisher publisher;
    private final Unleash unleash;

    public BookService(BookRepository repo, BookEventPublisher publisher, Unleash unleash)
    {
        this.repo = repo;
        this.publisher = publisher;
        this.unleash = unleash;
    }


    public List<Book> findAll()
    {
        isBookServiceEnabled();
        return repo.findAll();
    }


    public Book save(Book b)
    {
        isBookServiceEnabled();
        Book book = repo.save(b);
        publisher.publish(new BookCreatedEvent(book.getId(), book.getTitle()));
        return book;
    }


    public Book findById(Long id)
    {
        isBookServiceEnabled();
        return repo.findById(id).orElse(null);
    }


    public void deleteById(Long id)
    {
        isBookServiceEnabled();
        repo.deleteById(id);
    }


    private void isBookServiceEnabled()
    {
        if (!unleash.isEnabled(UNLEASH_FEATURE_FLAG_BOOK_SERVICE))
        {
            throw new ServiceUnavailableException("Book service disabled");
        }
    }
}