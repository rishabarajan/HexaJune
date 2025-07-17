import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, catchError, throwError } from 'rxjs';
//import { AssetRequest } from '../../core/models/asset-request.model';
import { AssetRequest } from '../../classes/asset-request.model';
@Injectable({
  providedIn: 'root'
})
export class AssetRequestService {
  private baseUrl = 'http://localhost:8886/api/assetrequests';

  constructor(private http: HttpClient) {}

  requestAsset(assetRequest: Partial<AssetRequest>): Observable<any> {
    return this.http.post(`${this.baseUrl}/request`, assetRequest);
  }
getApprovedRequestsByEmployee(employeeId: number): Observable<AssetRequest[]> {
  return this.http.get<AssetRequest[]>(`${this.baseUrl}/approved/employee/${employeeId}`);
}

  updateRequestStatus(requestId: number, status: string): Observable<any> {
    const token = localStorage.getItem('token');
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    });

    return this.http.put(`${this.baseUrl}/update-status/${requestId}`, { status }, { headers }).pipe(
      catchError((error) => {
        console.error('Update error:', error);
        return throwError(() => error);
      })
    );
  }

  getAllRequests(): Observable<AssetRequest[]> {
    return this.http.get<AssetRequest[]>(`${this.baseUrl}/all`);
  }

  getRequestsByStatus(status: string): Observable<AssetRequest[]> {
    return this.http.get<AssetRequest[]>(`${this.baseUrl}/byStatus/${status}`);
  }
}
