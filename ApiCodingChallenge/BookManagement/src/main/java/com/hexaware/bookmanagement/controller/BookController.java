package com.hexaware.bookmanagement.controller;

import com.hexaware.bookmanagement.model.Book;
import com.hexaware.bookmanagement.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:4200")
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
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        Book savedBook = bookService.addBook(book);
        return ResponseEntity.status(201).body(savedBook);  // 201 Created
    }

    @PutMapping("/update/{isbn}")
    public ResponseEntity<Void> updateBook(@PathVariable String isbn, @RequestBody Book updatedBook) {
        String result = bookService.updateBook(isbn, updatedBook);
        if (result.equals("Book updated successfully")) {
            return ResponseEntity.noContent().build(); // returns 204 No Content
        } else {
            return ResponseEntity.notFound().build();  // returns 404 if not found
        }
    }

    @DeleteMapping("/delete/{isbn}")
    public ResponseEntity<String> deleteBook(@PathVariable String isbn) {
        String result = bookService.deleteBook(isbn);
        if (result.equals("Book deleted successfully")) {
            return ResponseEntity.ok("{\"message\": \"" + result + "\"}");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body("{\"message\": \"" + result + "\"}");
        }
    }
}

