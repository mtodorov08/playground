package com.example.bookservice.service;


import java.util.List;
import org.springframework.stereotype.Service;
import com.example.bookservice.model.Book;
import com.example.bookservice.repository.BookRepository;


@Service
public class BookService
{
    private final BookRepository repo;

    public BookService(BookRepository repo)
    {
        this.repo = repo;
    }


    public List<Book> findAll()
    {
        return repo.findAll();
    }


    public Book save(Book b)
    {
        return repo.save(b);
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