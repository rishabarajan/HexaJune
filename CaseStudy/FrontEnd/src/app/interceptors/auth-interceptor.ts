import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { Router } from '@angular/router';
import { catchError, throwError } from 'rxjs';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const router = inject(Router);
  const token = localStorage.getItem('token');
  
  // Clone request and add auth header if token exists
  const authReq = token 
    ? req.clone({
        setHeaders: {
          Authorization: `Bearer ${token}`
        }
      })
    : req;
  return next(authReq).pipe(
    catchError((error) => {
      // Handle 401 Unauthorized responses
      if (error.status === 401) {
        localStorage.removeItem('token');
        router.navigate(['/login']);
      }
      
      return throwError(() => error);
    })
  );
};