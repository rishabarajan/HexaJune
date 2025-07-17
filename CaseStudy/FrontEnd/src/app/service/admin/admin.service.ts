import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AdminService {
  private baseUrl = 'http://localhost:8886/api/admin';

  constructor(private http: HttpClient) {}

  addAdmin(adminData: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/add`, adminData);
  }

  adminLogin(credentials: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/login`, credentials);
  }

  getAllAdmins(): Observable<any> {
    return this.http.get(`${this.baseUrl}/all`);
  }
}