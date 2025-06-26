package com.hexaware.bookmanagement.controller;

import com.hexaware.bookmanagement.model.Book;
import com.hexaware.bookmanagement.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/all")
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = bookService.getAllBooks();
        return ResponseEntity.ok(books);
    }

    @GetMapping("/{isbn}")
    public ResponseEntity<Book> getBookByIsbn(@PathVariable String isbn) {
        return bookService.getBookByIsbn(isbn)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @PostMapping("/add")
    public ResponseEntity<String> addBook(@RequestBody Book book) {
        String result = bookService.addBook(book);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/update/{isbn}")
    public ResponseEntity<String> updateBook(@PathVariable String isbn, @RequestBody Book updatedBook) {
        String result = bookService.updateBook(isbn, updatedBook);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/delete/{isbn}")
    public ResponseEntity<String> deleteBook(@PathVariable String isbn) {
        String result = bookService.deleteBook(isbn);
        return ResponseEntity.ok(result);
    }
}
