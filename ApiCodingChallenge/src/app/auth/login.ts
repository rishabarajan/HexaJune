import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
//import { AuthService } from '../core/services/auth.service';
//import { TokenService } from '../core/services/token.service';
import { User } from '../core/models/user.model';
import { AuthService } from '../core/services/auth';
import { TokenService } from '../core/services/token';
@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './login.html',
  styleUrls: ['./login.scss']
})
export class LoginComponent {
  user: User = { username: '', password: '' };
  errorMessage = '';

  constructor(
    private authService: AuthService,
    private tokenService: TokenService,
    private router: Router
  ) {}

  login(): void {
    this.authService.login(this.user).subscribe({
      next: (token: string) => {
        this.tokenService.setToken(token);
        this.router.navigate(['/books']);
      },
      error: () => {
        this.errorMessage = 'Invalid username or password';
      }
    });
  }
}
