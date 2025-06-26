package com.hexaware.bookmanagement.service;

import com.hexaware.bookmanagement.model.Book;
import com.hexaware.bookmanagement.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Optional<Book> getBookByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn);
    }

    public String addBook(Book book) {
        bookRepository.save(book);
        return "New book added";
    }

    public String updateBook(String isbn, Book updatedBook) {
        return bookRepository.findByIsbn(isbn).map(book -> {
            book.setTitle(updatedBook.getTitle());
            book.setAuthor(updatedBook.getAuthor());
            book.setPublicationYear(updatedBook.getPublicationYear());
            bookRepository.save(book);
            return "Book updated successfully";
        }).orElse("Book not found");
    }

    public String deleteBook(String isbn) {
        return bookRepository.findByIsbn(isbn).map(book -> {
            bookRepository.delete(book);
            return "Book deleted successfully";
        }).orElse("Book not found");
    }
}
