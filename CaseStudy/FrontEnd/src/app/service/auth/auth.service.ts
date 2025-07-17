import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, catchError, tap, throwError } from 'rxjs';

interface LoginResponse {
 // name(arg0: string, name: any): unknown;
  token: string;
  role: string;
  userId: number;
  name: string;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private baseUrl = 'http://localhost:8886/api/auth';

  constructor(private http: HttpClient) {}

  login(credentials: { username: string, password: string }): Observable<LoginResponse> {
  return this.http.post<LoginResponse>(`${this.baseUrl}/login`, {
    userName: credentials.username,
    password: credentials.password
  }).pipe(
    tap((response: LoginResponse) => {
      this.setToken(response.token);            
      this.setUserRole(response.role);           
      this.userName = response.name;            
    }),
    catchError((error: HttpErrorResponse) => {
      let errorMessage = 'Login failed';
      if (error.error?.error) {
        errorMessage = error.error.error;
      }
      return throwError(() => new Error(errorMessage));
    })
  );
}


  setToken(token: string): void {
    localStorage.setItem('token', token);
  }

  setUserRole(role: string): void {
    localStorage.setItem('role', role);
  }
// ✅ Getter and Setter for userName
  set userName(name: string) {
    localStorage.setItem('userName', name);
  }

  get userName(): string {
    return localStorage.getItem('userName') || 'User';
  }
  getToken(): string | null {
    return localStorage.getItem('token');
  }

  getUserRole(): string | null {
    return localStorage.getItem('role');
  }

  logout(): void {
    localStorage.removeItem('token');
    localStorage.removeItem('role');
    localStorage.removeItem('username');
  }
}