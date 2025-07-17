import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class EmployeeService {
  private baseUrl = 'http://localhost:8886/api/employees';

  constructor(private http: HttpClient) {}

  addEmployee(employeeData: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/add`, employeeData);
  }

  updateEmployee(employeeData: any): Observable<any> {
    return this.http.put(`${this.baseUrl}/update`, employeeData);
  }

  deleteEmployee(employeeId: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/delete/${employeeId}`);
  }

  getAllEmployees(): Observable<any> {
    return this.http.get(`${this.baseUrl}/all`);
  }

  searchEmployeeById(employeeId: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/search/${employeeId}`);
  }

  searchEmployeeByUsername(username: string): Observable<any> {
    return this.http.get(`${this.baseUrl}/searchByUsername/${username}`);
  }
}