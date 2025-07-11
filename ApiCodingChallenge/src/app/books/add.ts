import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
//import { BookService } from 'src/app/core/services/book.service';
//import { Book } from 'src/app/core/models/book.model';
import { BookService } from '../core/services/book';
import { Book } from '../core/models/book.model';
@Component({
  selector: 'app-add',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './add.html',
  styleUrls: ['./add.scss']
})
export class AddComponent {
  book: Book = {
    title: '',
    author: '',
    isbn: '',
    publicationYear: new Date().getFullYear()
  };

  isSubmitting = false;
  message = '';

  constructor(private bookService: BookService, private router: Router) {}

  addBook() {
  this.isSubmitting = true;
  this.bookService.add(this.book).subscribe({
    next: (res) => {
      this.message = 'Book added successfully!';
      this.router.navigate(['/books']);
    },
    error: (err) => {
      console.error('Add book error', err);
      this.message = 'Failed to add book';
      this.isSubmitting = false;
    }
  });

}

}
