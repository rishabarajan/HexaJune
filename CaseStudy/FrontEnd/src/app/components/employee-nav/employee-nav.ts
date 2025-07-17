import { Component } from '@angular/core';
import { RouterLink, RouterLinkActive } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-employee-nav',
  standalone: true,
  imports: [CommonModule, RouterLink, RouterLinkActive],
  templateUrl: './employee-nav.html',
  styleUrls: ['./employee-nav.scss']
})
export class EmployeeNavComponent {
  navItems = [
    { path: '/employee/dashboard', label: 'Dashboard', icon: 'dashboard' },
    { path: '/employee/assets', label: 'My Assets', icon: 'inventory' },
    { path: '/employee/request-asset', label: 'Request Asset', icon: 'add_circle' },
    { path: '/employee/service-request', label: 'Service Request', icon: 'build' }
  ];
}