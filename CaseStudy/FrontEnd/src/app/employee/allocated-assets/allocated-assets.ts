import { Component, OnInit } from '@angular/core';
import { AssetService } from '../../service/asset/asset.service';
import { CommonModule, DatePipe } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatTooltipModule } from '@angular/material/tooltip';
import { RouterModule } from '@angular/router';
import { AssetRequestService } from '../../service/asset-request/asset-request.service';
import { AssetRequest } from '../../classes/asset-request.model';
@Component({
  selector: 'app-allocated-assets',
  standalone: true,
  imports: [
    CommonModule,
    MatIconModule,
    MatButtonModule,
    MatCardModule,
    MatProgressSpinnerModule,
    MatTooltipModule,
    RouterModule,
    DatePipe
  ],
  templateUrl: './allocated-assets.html',
  styleUrls: ['./allocated-assets.scss']
})
export class AllocatedAssetsComponent implements OnInit {
  allocatedAssets: any[] = [];
  isLoading = true;

  constructor(private assetService: AssetService,private assetRequestService: AssetRequestService) {}

  ngOnInit(): void {
    this.loadAllocatedAssets();
  }

  loadAllocatedAssets(): void {
  const employeeId = Number(localStorage.getItem('userId'));

  this.assetRequestService.getApprovedRequestsByEmployee(+employeeId).subscribe({
  next: (requests: AssetRequest[]) => {
    this.allocatedAssets = requests;
    this.isLoading = false;
  },
  error: (err: any) => {
    console.error('Error loading allocated assets:', err);
    this.isLoading = false;
  }
});

}


  getAssetIcon(assetType: string): string {
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