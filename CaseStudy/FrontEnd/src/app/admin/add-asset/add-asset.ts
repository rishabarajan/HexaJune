import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { MatSnackBarModule, MatSnackBar } from '@angular/material/snack-bar';
import { AssetService } from '../../service/asset/asset.service';

@Component({
  selector: 'app-add-asset',
  standalone: true,
  imports: [CommonModule, FormsModule, MatSnackBarModule],
  templateUrl: './add-asset.html',
  styleUrls: ['./add-asset.scss']
})
export class AddAssetComponent {
  assetData = {
    assetName: '',
    assetCategory: '',
    assetModel: '',
    status: 'Available'
  };

  constructor(
    private assetService: AssetService,
    private snackBar: MatSnackBar,
    private router: Router
  ) {}

  onSubmit(): void {
    this.assetService.addAsset(this.assetData).subscribe({
      next: () => {
        this.snackBar.open('Asset added successfully', 'Close', { duration: 3000 });
        this.router.navigate(['/admin/assets']);
      },
      error: () => {
        this.snackBar.open('Failed to add asset', 'Close', { duration: 3000 });
      }
    });
  }
}
