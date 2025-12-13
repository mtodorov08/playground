package com.example.bookservice.controller;



import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.bookservice.repository.BookRepository;
import com.example.bookservice.service.BookService;


@RunWith(SpringRunner.class)
@WebMvcTest
@AutoConfigureMockMvc
public class BookControllerTest
{
    @MockitoBean
    private BookRepository bookRepository;

    @MockitoBean
    private BookService bookService;

    @Autowired
    BookController bookController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void whenPostRequestToBooksAndValidBook_thenCorrectResponse()
        throws Exception
    {
        String book = "{\"title\": \"1984\", \"author\" : \"O'Neel\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/api/books")
                                              .content(book)
                                              .contentType(MediaType.APPLICATION_JSON)
                                              .with(SecurityMockMvcRequestPostProcessors.httpBasic("user", "pass"))
                                              .with(SecurityMockMvcRequestPostProcessors.csrf()))
               .andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
    public void whenPostRequestToBooksAndInValidBook_thenCorrectResponse()
        throws Exception
    {
        String book = "{\"title\": \"\", \"author\" : \"O'Neel\"}";
        mockMvc.perform(MockMvcRequestBuilders.post("/api/books")
                                              .content(book)
                                              .contentType(MediaType.APPLICATION_JSON)
                                              .with(SecurityMockMvcRequestPostProcessors.httpBasic("user", "pass"))
                                              .with(SecurityMockMvcRequestPostProcessors.csrf()))
               .andExpect(MockMvcResultMatchers.status().isBadRequest())
               .andExpect(MockMvcResultMatchers.jsonPath("$.title", Is.is("Title is mandatory")))
               .andExpect(MockMvcResultMatchers.content()
                                               .contentType(MediaType.APPLICATION_JSON));
    }

}
