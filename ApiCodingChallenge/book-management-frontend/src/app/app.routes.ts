import { Routes } from '@angular/router';
import { provideRouter } from '@angular/router';
//import { authGuard } from './core/guards/auth.guard';
import { authGuard } from './core/guards/auth-guard';
export const routes: Routes = [
  {
    path: '',
    redirectTo: 'login',
    pathMatch: 'full',
  },
  {
    path: 'login',
    loadComponent: () => import('./auth/login').then(m => m.LoginComponent),
  },
  {
    path: 'register',
    loadComponent: () => import('./auth/register').then(m => m.RegisterComponent),
  },
  {
    path: 'books',
    loadComponent: () => import('./books/list').then(m => m.ListComponent),
    canActivate: [authGuard]
  },
  {
    path: 'books/add',
    loadComponent: () => import('./books/add').then(m => m.AddComponent),
    canActivate: [authGuard]
  },
  {
    path: 'books/edit/:isbn',
    loadComponent: () => import('./books/update').then(m => m.UpdateComponent),
    canActivate: [authGuard]
  },
  {
    path: 'books/view/:isbn',
    loadComponent: () => import('./books/view').then(m => m.ViewComponent),
    canActivate: [authGuard]
  },
];

export const AppRouter = provideRouter(routes);
