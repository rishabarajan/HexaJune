import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators, FormsModule } from '@angular/forms';
import { AuthService } from '../../service/auth/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, FormsModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  loginForm: FormGroup;
  isLoading = false;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {
    this.loginForm = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  onSubmit(): void {
  if (this.loginForm.invalid) return;

  this.isLoading = true;
  const username = this.loginForm.value.username;

  this.authService.login(this.loginForm.value).subscribe({
    next: (res) => {
      localStorage.setItem('token', res.token);
      localStorage.setItem('role', res.role);
      localStorage.setItem('userName', res.name); 
      localStorage.setItem('userId', res.userId.toString());
      // Role-based navigation
      if (res.role === 'ADMIN') {
        this.router.navigate(['/admin/dashboard']);
      } else if (res.role === 'EMPLOYEE') {
        this.router.navigate(['/employee/dashboard']);
      }
      this.isLoading = false;
    },
    error: (err) => {
      console.error('Login failed', err);
      alert(err.message || 'Invalid credentials');
      this.isLoading = false;
    }
  });

  }
}