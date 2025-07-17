import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, NgForm } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatSelectModule } from '@angular/material/select';
import { MatCardModule } from '@angular/material/card';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { MatIconModule } from '@angular/material/icon'; // Added for mat-icon
import { MatFormFieldModule } from '@angular/material/form-field'; // Added for form fields
import { AssetRequestService } from '../../service/asset-request/asset-request.service';
import { AssetService } from '../../service/asset/asset.service';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';

@Component({
  selector: 'app-request-asset',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    MatInputModule,
    MatButtonModule,
    MatSelectModule,
    MatCardModule,
    MatSnackBarModule,
    MatIconModule,        // Added for mat-icon
    MatFormFieldModule,
    MatDatepickerModule,
  MatNativeDateModule    // Added for form fields
  ],
  templateUrl: './request-asset.html',
  styleUrls: ['./request-asset.scss']
})
export class RequestAssetComponent implements OnInit {
 requestData = {
  employeeId: Number(localStorage.getItem('userId') || 0),
  assetId: 0,
  assetName: '',
  requestType: '',
  description: '',
  issueType: '',
  requestDate: new Date().toISOString().split('T')[0],  // YYYY-MM-DD
  status: 'PENDING'
};
  assetTypes: string[] = ['Laptop', 'Monitor', 'Phone', 'Tablet', 'Furniture', 'Accessory'];
  requestTypes: string[] = ['Borrow', 'Return', 'Repair'];

  isSubmitting = false;
  assets: any;

  constructor(
    private assetRequestService: AssetRequestService,
    private snackBar: MatSnackBar,
    private assetService: AssetService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.assetService.getAllAssets().subscribe({
      next: (res) => (this.assets = res),
      error: (err) => console.error('Failed to load assets', err)
    });

    // Read query params and prefill asset info
    this.route.queryParams.subscribe(params => {
      if (params['assetId']) {
        this.requestData.assetId = +params['assetId'];
      }
      if (params['assetName']) {
        this.requestData.assetName = params['assetName'];
      }
    });
  }

  submitRequest(form: NgForm) {
    if (form.invalid) return;

    this.isSubmitting = true;

    this.assetRequestService.requestAsset(this.requestData).subscribe({
      next: () => {
        this.snackBar.open('Request submitted successfully!', 'Close', { duration: 3000 });
        this.router.navigate(['/employee/dashboard']);
      },
      error: (err) => {
        console.error(err);
        this.snackBar.open('Failed to submit request.', 'Close', { duration: 3000 });
        this.isSubmitting = false;
      }
    });
  }
}