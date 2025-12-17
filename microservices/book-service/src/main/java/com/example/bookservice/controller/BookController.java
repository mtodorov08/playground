package com.example.bookservice.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookservice.model.Book;
import com.example.bookservice.service.BookService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/books")
public class BookController
{
    private final BookService service;

    @Value("${POD_NAME:unknown}")
    private String podName;

    public BookController(BookService service)
    {
        this.service = service;
    }


    @GetMapping
    public ResponseEntity<List<Book>> all()
    {
        List<Book> books = service.findAll();

        return ResponseEntity.ok()
                        .header("X-Book-Service-Pod", podName)
                        .body(books);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Book> get(@PathVariable Long id)
    {
        Book b = service.findById(id);
        return (b != null) ? ResponseEntity.ok(b) : ResponseEntity.notFound().build();
    }


    @PostMapping
    public Book create(@Valid @RequestBody Book book)
    {
        return service.save(book);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Book> update(@PathVariable Long id, @RequestBody Book book)
    {
        Book existing = service.findById(id);
        if (existing == null) return ResponseEntity.notFound().build();
        existing.setTitle(book.getTitle());
        existing.setAuthor(book.getAuthor());
        return ResponseEntity.ok(service.save(existing));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id)
    {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex)
    {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) ->
        {
            String fieldName = ((FieldError)error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
