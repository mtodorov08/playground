package com.example.bookservice.service;


import java.util.List;

import org.springframework.stereotype.Service;

import com.example.bookservice.model.Book;
import com.example.bookservice.model.BookCreatedEvent;
import com.example.bookservice.repository.BookRepository;


@Service
public class BookService
{
    private final BookRepository repo;
    private final BookEventPublisher publisher;

    public BookService(BookRepository repo, BookEventPublisher publisher)
    {
        this.repo = repo;
        this.publisher = publisher;
    }


    public List<Book> findAll()
    {
        return repo.findAll();
    }


    public Book save(Book b)
    {
        Book book = repo.save(b);
        publisher.publish(new BookCreatedEvent(book.getId(), book.getTitle()));
        return book;
    }


    public Book findById(Long id)
    {
        return repo.findById(id).orElse(null);
    }


    public void deleteById(Long id)
    {
        repo.deleteById(id);
    }
}