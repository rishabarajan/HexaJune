import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { RouterModule } from '@angular/router';
import { AssetService } from '../../service/asset/asset.service';

@Component({
  selector: 'app-asset-management',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    MatIconModule,
    MatButtonModule,
    MatProgressSpinnerModule,
    RouterModule
  ],
  templateUrl: './asset-management.html',
  styleUrls: ['./asset-management.scss']
})
export class AssetManagementComponent implements OnInit {
  assets: any[] = [];
  filteredAssets: any[] = [];
  searchTerm: string = '';
  isLoading: boolean = true;
  categories: string[] = ['All', 'Laptop', 'Desktop', 'Mouse', 'Keyboard', 'Monitor', 'Other'];

  constructor(private assetService: AssetService) {}

  ngOnInit(): void {
    this.loadAssets();
  }

  loadAssets(): void {
    this.isLoading = true;
    this.assetService.getAllAssets().subscribe({
      next: (data: any) => {
        this.assets = data.map((item: any) => ({
          asset_id: item.assetId,
          asset_name: item.assetName,
          asset_category: item.assetCategory,
          asset_model: item.assetModel,
          status: item.status || 'Available'
        }));
        this.filteredAssets = [...this.assets];
        this.isLoading = false;
      },
      error: (error) => {
        console.error('Error loading assets:', error);
        this.isLoading = false;
      }
    });
  }

  searchAssets(): void {
    const term = this.searchTerm.toLowerCase();
    this.filteredAssets = this.assets.filter(asset =>
      (asset.asset_name?.toLowerCase() || '').includes(term) ||
      (asset.asset_category?.toLowerCase() || '').includes(term)
    );
  }

  filterByCategory(category: string): void {
    if (category === 'All') {
      this.filteredAssets = [...this.assets];
    } else {
      this.filteredAssets = this.assets.filter(asset =>
        asset.asset_category === category
      );
    }
  }

  // Add this method to fix the deleteAsset error
  deleteAsset(id: number): void {
  if (confirm('Are you sure you want to delete this asset?')) {
    const assetIndex = this.filteredAssets.findIndex(a => a.asset_id === id);
    if (assetIndex >= 0) {
      this.filteredAssets[assetIndex].isDeleting = true; // Add temporary flag
    }
    
    this.assetService.deleteAsset(id).subscribe({
      next: () => {
        this.assets = this.assets.filter(asset => asset.asset_id !== id);
        this.filteredAssets = this.filteredAssets.filter(asset => asset.asset_id !== id);
      },
      error: (error) => {
        console.error('Error deleting asset:', error);
        if (assetIndex >= 0) {
          this.filteredAssets[assetIndex].isDeleting = false;
        }
      }
    });
  }
}

  // Add this method to fix the getStatusClass error
  getStatusClass(status: string | undefined): string {
    if (!status) return 'available';
    return status.toLowerCase().replace(' ', '-');
  }

  // Utility method for category styling
  getCategoryClass(category: string | undefined): string {
    if (!category) return 'other';
    return category.toLowerCase().replace(' ', '-');
  }

  // Performance optimization for ngFor
  trackByAssetId(index: number, asset: any): number {
    return asset.asset_id;
  }
}