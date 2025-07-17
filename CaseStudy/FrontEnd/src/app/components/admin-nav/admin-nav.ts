import { Component } from '@angular/core';
import { RouterLink, RouterLinkActive } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-admin-nav',
  standalone: true,
  imports: [CommonModule, RouterLink, RouterLinkActive], // Required for router links
  templateUrl: './admin-nav.html',
  styleUrls: ['./admin-nav.scss']
})
export class AdminNavComponent {
  navItems = [
    { path: '/admin/dashboard', label: 'Dashboard', icon: 'dashboard' },
    { path: '/admin/employees', label: 'Employee Management', icon: 'people' },
    { path: '/admin/assets', label: 'Asset Management', icon: 'inventory' },
    { path: '/admin/reports', label: 'Audit & Reporting', icon: 'assessment' }
  ];
}