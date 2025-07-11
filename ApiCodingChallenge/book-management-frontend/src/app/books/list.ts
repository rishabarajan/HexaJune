import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
//import { BookService } from '../core/services/book.service';
import { Book } from '../core/models/book.model';
import { Router } from '@angular/router';
import { BookService } from '../core/services/book';
import { FormsModule } from '@angular/forms';
@Component({
  selector: 'app-list',
  standalone: true,
imports: [CommonModule, FormsModule],
  templateUrl: './list.html',
  styleUrls: ['./list.scss']
})
export class ListComponent implements OnInit {
  private bookService = inject(BookService);
  private router = inject(Router);

  books: Book[] = [];
  isLoading = true;
  error = '';

  ngOnInit(): void {
    this.bookService.getAll().subscribe({
      next: (data) => {
        this.books = data;
        this.isLoading = false;
      },
      error: () => {
        this.error = 'Failed to load books';
        this.isLoading = false;
      }
    });
  }
addBook() {
  this.router.navigate(['/books/add']);
}

  viewBook(isbn: string) {
    this.router.navigate(['/books/view', isbn]);
  }

  editBook(isbn: string) {
    this.router.navigate(['/books/edit', isbn]);
  }
searchText = '';

get filteredBooks(): Book[] {
  const query = this.searchText.toLowerCase();
  return this.books.filter(book =>
    book.title.toLowerCase().includes(query) ||
    book.isbn.toLowerCase().includes(query)
  );
}

 message = '';

deleteBook(isbn: string) {
  this.bookService.delete(isbn).subscribe({
    next: (response) => {
      this.books = this.books.filter(b => b.isbn !== isbn);
      this.message = 'Book deleted successfully!';
    },
    error: (err) => {
      console.error('Delete book error:', err);
      this.message = 'Failed to delete book.';
    }
  });
}
}
