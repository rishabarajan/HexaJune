import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AssetService } from '../../service/asset/asset.service';
import { EmployeeService } from '../../service/employee/employee.service';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { CommonModule } from '@angular/common';
import { AssetRequestService } from '../../service/asset-request/asset-request.service';

@Component({
  selector: 'app-admin-dashboard',
  templateUrl: './dashboard.html',
  styleUrls: ['./dashboard.scss'],
  standalone: true,
  imports: [
    CommonModule,
    MatButtonModule,
    MatCardModule,
    MatIconModule
  ]
})
export class AdminDashboardComponent implements OnInit {
  assetCount: number = 0;
  employeeCount: number = 0;
  pendingRequests: number = 5; // You'll need to implement this properly

  constructor(
    private assetService: AssetService,
    private employeeService: EmployeeService,
    private router: Router,
    private requestService: AssetRequestService
  ) {}

  ngOnInit(): void {
    this.loadDashboardData();
  }
logout(): void {
  localStorage.clear(); // or just remove token if you're using JWT
  this.router.navigate(['/login']); // redirect to login page
}

  loadDashboardData(): void {
  this.assetService.getAllAssets().subscribe(assets => {
    this.assetCount = assets.length;
  });

  this.employeeService.getAllEmployees().subscribe(employees => {
    this.employeeCount = employees.length;
  });

  this.requestService.getRequestsByStatus('PENDING').subscribe(requests => {
    this.pendingRequests = requests.length;
  });
}

  navigateToAssets(): void {
    this.router.navigate(['/admin/assets']);
  }

  navigateToEmployees(): void {
    this.router.navigate(['/admin/employees']);
  }

  navigateToRequests(): void {
    // Assuming you have a route for pending requests
    this.router.navigate(['/admin/requests']);
  }

  navigateToAddAsset(): void {
    this.router.navigate(['/admin/assets/add']);
  }

  navigateToAddEmployee(): void {
    this.router.navigate(['/admin/employees/add']);
  }
}