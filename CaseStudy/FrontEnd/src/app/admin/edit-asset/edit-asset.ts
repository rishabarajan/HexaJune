// edit-asset.component.ts - Complete working version
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatCardModule } from '@angular/material/card';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatIconModule } from '@angular/material/icon';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { AssetService } from '../../service/asset/asset.service';
//import { AuthService } from '../../auth/auth.service';
import { AuthService } from '../../service/auth/auth.service';
@Component({
  selector: 'app-edit-asset',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    MatButtonModule,
    MatInputModule,
    MatSelectModule,
    MatCardModule,
    MatIconModule,
    MatProgressSpinnerModule
  ],
  templateUrl: './edit-asset.html',
  styleUrls: ['./edit-asset.scss']
})
export class EditAssetComponent implements OnInit {
  assetId!: number;
  assetData: any = {
    assetId: 0,
    assetName: '',
    assetCategory: '',
    assetModel: '',
    status: 'Available'
  };
  categories = ['Laptop', 'Desktop', 'Mouse', 'Keyboard', 'Monitor', 'Other'];
  statuses = ['Available', 'Assigned', 'Under Maintenance', 'Retired'];
  isLoading = false;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private assetService: AssetService,
    private authService: AuthService,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.assetId = +this.route.snapshot.params['id'];
    this.loadAssetData();
  }

  loadAssetData(): void {
    this.isLoading = true;
    this.assetService.searchAssetById(this.assetId).subscribe({
      next: (data) => {
        this.assetData = data;
        this.isLoading = false;
      },
      error: (err) => {
        console.error('Error loading asset:', err);
        this.isLoading = false;
        this.showError('Failed to load asset details');
        if (err.status === 401 || err.status === 403) {
          this.router.navigate(['/login']);
        }
      }
    });
  }

  updateAsset(): void {
    // Validate form data
    if (!this.assetData.assetName || !this.assetData.assetCategory) {
      this.showError('Asset name and category are required');
      return;
    }

    this.isLoading = true;
    
    // Verify token exists
    if (!this.authService.getToken()) {
      this.showError('Session expired. Please login again.');
      this.router.navigate(['/login']);
      return;
    }

    this.assetService.updateAsset(this.assetData).subscribe({
      next: () => {
        this.isLoading = false;
        this.showSuccess('Asset updated successfully!');
        this.router.navigate(['/admin/assets']);
      },
      error: (err) => {
        this.isLoading = false;
        console.error('Update error:', err);
        
        if (err.status === 403) {
          this.showError('Permission denied. You need admin rights.');
        } else if (err.status === 401) {
          this.showError('Session expired. Please login again.');
          this.router.navigate(['/login']);
        } else {
          this.showError('Failed to update asset. Please try again.');
        }
      }
    });
  }

  private showSuccess(message: string): void {
    this.snackBar.open(message, 'Close', { 
      duration: 3000,
      panelClass: ['success-snackbar'] 
    });
  }

  private showError(message: string): void {
    this.snackBar.open(message, 'Close', { 
      duration: 5000,
      panelClass: ['error-snackbar'] 
    });
  }
}