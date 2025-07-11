import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { BookService } from '../core/services/book';
import { Book } from '../core/models/book.model';

@Component({
  selector: 'app-update',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './update.html',
  styleUrls: ['./update.scss']
})
export class UpdateComponent implements OnInit {
  book: Book = {
    title: '',
    author: '',
    isbn: '',
    publicationYear: new Date().getFullYear()
  };

  isSubmitting = false;
  message = '';
  isbn: string = '';

  constructor(
    private route: ActivatedRoute,
    private bookService: BookService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.isbn = this.route.snapshot.paramMap.get('isbn') || '';
    this.bookService.getByIsbn(this.isbn).subscribe({
      next: (data) => this.book = data,
      error: () => this.message = 'Failed to load book details.'
    });
  }

  updateBook(): void {
    this.isSubmitting = true;
    this.bookService.update(this.isbn, this.book).subscribe({
      next: () => {
  this.message = 'Book updated successfully!';
  this.router.navigate(['/books']);
},
      error: () => {
        this.message = 'Failed to update book.';
        this.isSubmitting = false;
      }
    });
  }
}
