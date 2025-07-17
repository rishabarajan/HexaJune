import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, Observable } from 'rxjs';
//import { AuthService } from './auth.service';
import { AuthService } from '../auth/auth.service';
@Injectable({
  providedIn: 'root'
})
export class AssetService {
  private baseUrl = 'http://localhost:8886/api/assets';

  constructor(private http: HttpClient, private authService: AuthService) {}

  private getHeaders(): HttpHeaders {
    const token = this.authService.getToken();
    return new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    });
  }

  addAsset(assetData: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/add`, assetData, {
      headers: this.getHeaders()
    });
  }

 // asset.service.ts - Add error handling
updateAsset(assetData: any): Observable<any> {
    const headers = this.getHeaders();
    console.log('Update request payload:', assetData); // Debug
    
    return this.http.put(
        `${this.baseUrl}/update/${assetData.assetId}`,
        assetData,
        { headers }
    ).pipe(
        catchError(error => {
            console.error('Update error:', error);
            throw error; // Rethrow for component to handle
        })
    );
}

  deleteAsset(assetId: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/delete/${assetId}`, {
      headers: this.getHeaders()
    });
  }

  getAllAssets(): Observable<any> {
    return this.http.get(`${this.baseUrl}/all`, {
      headers: this.getHeaders()
    });
  }

  searchAssetById(assetId: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/search/${assetId}`, {
      headers: this.getHeaders()
    });
  }

  searchAssetsByCategory(category: string): Observable<any> {
    return this.http.get(`${this.baseUrl}/searchByCategory/${category}`, {
      headers: this.getHeaders()
    });
  }
}