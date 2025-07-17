import { Component } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { AssetRequestService } from '../../../service/asset-request/asset-request.service';

interface DisplayedRequest {
  id: number;
  employee: { id: number; name: string };
  asset: { id: number; name: string };
  requestDate: string;
  status: string;
  requestType: string;
}

@Component({
  selector: 'app-request-management',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    MatTableModule,
    MatButtonModule,
    MatIconModule,
    MatFormFieldModule,
    MatInputModule,
    DatePipe,
    MatTooltipModule,
    MatProgressSpinnerModule,
  ],
  templateUrl: './request-management.html',
  styleUrls: ['./request-management.scss'],
})
export class RequestManagementComponent {
  searchTerm: string = '';
  dataSource: DisplayedRequest[] = [];
  filteredData: DisplayedRequest[] = [];
  loading = false;
  error: string | null = null;
  displayedColumns: string[] = ['id', 'employee', 'asset', 'requestDate', 'status', 'requestType', 'actions'];

  constructor(private requestService: AssetRequestService, private snackBar: MatSnackBar) {}

  ngOnInit(): void {
    this.loadPendingRequests();
  }

  loadPendingRequests(): void {
    this.loading = true;
    this.error = null;

    this.requestService.getRequestsByStatus('Pending').subscribe({
      next: (requests: any[]) => {
        this.dataSource = requests.map(r => this.mapToDisplayedRequest(r));
        this.filteredData = [...this.dataSource];
        this.loading = false;
      },
      error: err => {
        this.error = 'Failed to load requests. Please try again.';
        this.loading = false;
        console.error(err);
      },
    });
  }

  searchRequests(): void {
    const term = this.searchTerm.trim().toLowerCase();
    this.filteredData = this.dataSource.filter(req =>
      req.employee.name.toLowerCase().includes(term) ||
      req.asset.name.toLowerCase().includes(term)
    );
  }

  approveRequest(id: number): void {
    this.updateStatus(id, 'Approved');
  }

  rejectRequest(id: number): void {
    this.updateStatus(id, 'Rejected');
  }

  private updateStatus(requestId: number, status: string): void {
    const originalStatus = this.dataSource.find(r => r.id === requestId)?.status;
    this.dataSource = this.dataSource.map(r =>
      r.id === requestId ? { ...r, status } : r
    );
    this.filteredData = [...this.dataSource]; // Ensure view updates

    this.requestService.updateRequestStatus(requestId, status).subscribe({
      next: () => {
        this.snackBar.open(`Request ${status.toLowerCase()} successfully`, 'Close', { duration: 3000 });
      },
      error: err => {
        this.dataSource = this.dataSource.map(r =>
          r.id === requestId ? { ...r, status: originalStatus } : r
        );
        this.filteredData = [...this.dataSource];
        this.snackBar.open(`Failed to update status`, 'Close', { duration: 3000 });
        console.error(err);
      },
    });
  }

  private mapToDisplayedRequest(request: any): DisplayedRequest {
    return {
      id: request.requestId,
      employee: {
        id: request.employee?.employeeId || 0,
        name: request.employee?.employeeName || 'Unknown Employee',
      },
      asset: {
        id: request.asset?.assetId || 0,
        name: request.asset?.assetName || 'Unknown Asset',
      },
      requestDate: request.requestDate,
      status: request.status,
      requestType: request.requestType,
    };
  }

  viewDetails(id: number): void {
    console.log('View details for request:', id);
  }
}
