import { Component, OnInit } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { AssetService } from '../../service/asset/asset.service';
import { RouterModule } from '@angular/router';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatMenuModule } from '@angular/material/menu';
import { AssetRequestService } from '../../service/asset-request/asset-request.service';
@Component({
  selector: 'app-employee-dashboard',
  standalone: true,
  imports: [
    CommonModule,
    RouterModule,
    MatIconModule,
    MatButtonModule,
    MatCardModule,
    MatProgressSpinnerModule,
    MatTooltipModule,
    MatMenuModule,
    DatePipe
  ],
  templateUrl: './dashboard.html',
  styleUrls: ['./dashboard.scss']
})
export class EmployeeDashboardComponent implements OnInit {
  allocatedAssets: any[] = [];
  isLoading = true;
  userName = 'User';

  quickActions = [
    { icon: 'add', label: 'Request New Asset', route: '/employee/request-asset' },
    { icon: 'search', label: 'Browse Assets', route: '/employee/assets' }
  ];

  constructor(private assetService: AssetService, private assetRequestService: AssetRequestService,) {}

  ngOnInit(): void {
    this.loadEmployeeData();
    this.userName = localStorage.getItem('userName') || 'User';
  }

 loadEmployeeData(): void {
  const employeeId = localStorage.getItem('userId');
  if (!employeeId) return;

  this.assetRequestService.getApprovedRequestsByEmployee(+employeeId).subscribe({
    next: (requests) => {
      this.allocatedAssets = requests;
      this.isLoading = false;
    },
    error: (err) => {
      console.error('Error loading allocated assets:', err);
      this.isLoading = false;
    }
  });
}
logout(): void {
  localStorage.clear(); // or just remove 'userId' and 'userName' if needed
  location.href = '/login'; // or use Angular router if available
}

 getAssetIcon(assetType: string | undefined): string {
  if (!assetType) return 'devices';
  const icons: Record<string, string> = {
    'laptop': 'laptop_mac',
    'phone': 'smartphone',
    'tablet': 'tablet_mac',
    'monitor': 'desktop_windows',
    'accessory': 'keyboard'
  };
  return icons[assetType.toLowerCase()] || 'devices';
}


   
}