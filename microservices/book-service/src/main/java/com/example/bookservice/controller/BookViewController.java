package com.example.bookservice.controller;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.bookservice.repository.BookRepository;


@Controller
public class BookViewController
{

    private final BookRepository bookRepository;

    @Value("${POD_NAME:unknown}")
    private String podName;

    public BookViewController(BookRepository bookRepository)
    {
        this.bookRepository = bookRepository;
    }


    @GetMapping("/books/view")
    public String viewBooks(Model model)
    {
        model.addAttribute("books", bookRepository.findAll());
        model.addAttribute("podName", podName);
        return "books"; // thymeleaf template name
    }
}
