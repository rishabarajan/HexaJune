import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule, Router } from '@angular/router';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { AssetService } from '../../service/asset/asset.service';

@Component({
  selector: 'app-view-assets',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    RouterModule,
    MatIconModule,
    MatButtonModule,
    MatProgressSpinnerModule
  ],
  templateUrl: './view-assets.html',
  styleUrls: ['./view-assets.scss']
})
export class ViewAssetsComponent implements OnInit {
  assets: any[] = [];
  filteredAssets: any[] = [];
  searchTerm: string = '';
  selectedCategory: string = 'Category';
  categories: string[] = ['Category', 'Laptop', 'Desktop', 'Monitor', 'Furniture', 'Accessory', 'Other'];
  isLoading: boolean = true;

  constructor(private assetService: AssetService, private router: Router) {}

  ngOnInit(): void {
    this.fetchAssets();
  }

  fetchAssets(): void {
  this.assetService.getAllAssets().subscribe({
    next: (data: any) => {
  console.log('Fetched assets:', data);

  if (data.length > 0) {
    console.log('First asset fields:', Object.keys(data[0]), data[0]);
  }

  this.assets = data.map((item: any) => ({
    asset_id: item.assetId,
    asset_name: item.assetName,
    asset_category: item.assetCategory,
    asset_model: item.assetModel,
    status: item.status || 'Available',
    description: item.description || item.assetDescription || 'No description provided'
  }));

  this.applyFilters();
  this.isLoading = false;
}

  });
}
statusFilter: string = 'Status';
statusOptions: string[] = ['Status', 'Available', 'Assigned', 'Maintenance', 'Retired'];

  applyFilters(): void {
  let filtered = [...this.assets];

  if (this.selectedCategory !== 'Category') {
    filtered = filtered.filter(asset => asset.asset_category === this.selectedCategory);
  }

  if (this.statusFilter !== 'Status') {
    filtered = filtered.filter(asset => asset.status.toLowerCase() === this.statusFilter.toLowerCase());
  }

  if (this.searchTerm.trim()) {
    const term = this.searchTerm.toLowerCase();
    filtered = filtered.filter(asset =>
      asset.asset_name.toLowerCase().includes(term) ||
      asset.asset_category.toLowerCase().includes(term)
    );
  }

  this.filteredAssets = filtered;
}
onStatusChange(status: string): void {
  this.statusFilter = status;
  this.applyFilters();
}


  onSearchChange(): void {
    this.applyFilters();
  }

  onCategoryChange(category: string): void {
    this.selectedCategory = category;
    this.applyFilters();
  }


  viewAssetDetails(assetId: number): void {
    this.router.navigate(['/employee/request-asset'], {
      queryParams: { assetId }
    });
  }
  searchAssets(): void {
  this.applyFilters();
}

filterByCategory(category: string): void {
  this.selectedCategory = category;
  this.applyFilters();
}

getStatusClass(status: string): string {
  switch (status.toLowerCase()) {
    case 'available': return 'status-available';
    case 'assigned': return 'status-assigned';
    case 'maintenance': return 'status-maintenance';
    case 'retired': return 'status-retired';
    default: return 'status-default';
  }
}


getCategoryClass(category: string): string {
  switch ((category || '').toLowerCase()) {
    case 'laptop':
      return 'badge-laptop';
    case 'desktop':
      return 'badge-desktop';
    case 'monitor':
      return 'badge-monitor';
    case 'furniture':
      return 'badge-furniture';
    case 'accessory':
      return 'badge-accessory';
    default:
      return 'badge-other';
  }
}

trackByAssetId(index: number, asset: any): number {
  return asset.asset_id;
}

}
