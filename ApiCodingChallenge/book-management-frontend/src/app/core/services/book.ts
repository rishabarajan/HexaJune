import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Book } from '../models/book.model';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class BookService {
  private http = inject(HttpClient);
  private baseUrl = 'http://localhost:2525/api/books';

  getAll(): Observable<Book[]> {
    return this.http.get<Book[]>(`${this.baseUrl}/all`);
  }

  getByIsbn(isbn: string): Observable<Book> {
    return this.http.get<Book>(`${this.baseUrl}/${isbn}`);
  }

 add(book: Book): Observable<Book> {
  return this.http.post<Book>(`${this.baseUrl}/add`, book); // ✅ CORRECT
}



  update(isbn: string, book: Book): Observable<any> {
    return this.http.put(`${this.baseUrl}/update/${isbn}`, book);
  }

  delete(isbn: string): Observable<any> {
    return this.http.delete(`${this.baseUrl}/delete/${isbn}`);
  }
}
