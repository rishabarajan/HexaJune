package com.hexaware.bookmanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hexaware.bookmanagement.model.Book;
import com.hexaware.bookmanagement.service.BookService;
import com.hexaware.bookmanagement.security.JwtUtil;
import com.hexaware.bookmanagement.service.CustomUserDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(
    controllers = BookController.class,
    excludeAutoConfiguration = {
        org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class,
        org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration.class
    }
)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

  
    @MockBean
    private JwtUtil jwtUtil;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private Book sampleBook;

    @BeforeEach
    void setUp() {
        sampleBook = new Book();
        sampleBook.setTitle("Test Book");
        sampleBook.setAuthor("Author Name");
        sampleBook.setIsbn("1234567890");
        sampleBook.setPublicationYear(2024);
    }

    @Test
    void testGetAllBooks() throws Exception {
        when(bookService.getAllBooks()).thenReturn(List.of(sampleBook));

        mockMvc.perform(get("/api/books/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].isbn").value("1234567890"));
    }

    @Test
    void testGetBookByIsbn() throws Exception {
        when(bookService.getBookByIsbn("1234567890")).thenReturn(Optional.of(sampleBook));

        mockMvc.perform(get("/api/books/1234567890"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Book"));
    }

    @Test
    void testAddBook() throws Exception {
        when(bookService.addBook(any(Book.class))).thenReturn("New book added");

        mockMvc.perform(post("/api/books/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleBook)))
                .andExpect(status().isOk())
                .andExpect(content().string("New book added"));
    }

    @Test
    void testUpdateBook() throws Exception {
        when(bookService.updateBook(Mockito.eq("1234567890"), any(Book.class)))
                .thenReturn("Book updated successfully");

        mockMvc.perform(put("/api/books/update/1234567890")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleBook)))
                .andExpect(status().isOk())
                .andExpect(content().string("Book updated successfully"));
    }

    @Test
    void testDeleteBook() throws Exception {
        when(bookService.deleteBook("1234567890")).thenReturn("Book deleted successfully");

        mockMvc.perform(delete("/api/books/delete/1234567890"))
                .andExpect(status().isOk())
                .andExpect(content().string("Book deleted successfully"));
    }
}
