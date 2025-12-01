package com.example.bookservice.controller;


import com.example.bookservice.model.Book;
import com.example.bookservice.repository.BookRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class BookViewController
{

    private final BookRepository bookRepository;

    public BookViewController(BookRepository bookRepository)
    {
        this.bookRepository = bookRepository;
    }


    @GetMapping("/books/view")
    public String viewBooks(Model model)
    {
        model.addAttribute("books", bookRepository.findAll());
        return "books"; // thymeleaf template name
    }
}
