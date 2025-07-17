import { Routes } from '@angular/router';
import { LoginComponent } from './auth/login/login.component';
import { AdminDashboardComponent } from './admin/dashboard/dashboard';
import { EmployeeManagementComponent } from './admin/employee-management/employee-management';
import { AssetManagementComponent } from './admin/asset-management/asset-management';
import { AuditReportingComponent } from './admin/audit-reporting/audit-reporting';
import { EmployeeDashboardComponent } from './employee/dashboard/dashboard';
import { ViewAssetsComponent } from './employee/view-assets/view-assets';
import { RequestAssetComponent } from './employee/request-asset/request-asset';
import { AuthGuard } from './guards/auth-guard';
import { RoleGuard } from './guards/role-guard';
import { RequestManagementComponent } from './admin/request-management/request-management/request-management';
import { EditAssetComponent } from './admin/edit-asset/edit-asset';
import { AddAssetComponent } from './admin/add-asset/add-asset';
import { AllocatedAssetsComponent } from './employee/allocated-assets/allocated-assets';

export const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: '', redirectTo: '/login', pathMatch: 'full' },

  // Admin routes
  {
    path: 'admin/dashboard',
    component: AdminDashboardComponent,
    canActivate: [AuthGuard, RoleGuard],
    data: { roles: ['ADMIN'] }
  },
  {
    path: 'admin/employees',
    component: EmployeeManagementComponent,
    canActivate: [AuthGuard, RoleGuard],
    data: { roles: ['ADMIN'] }
  },
  {
    path: 'admin/assets',
    component: AssetManagementComponent,
    canActivate: [AuthGuard, RoleGuard],
    data: { roles: ['ADMIN'] }
  },
  {
    path: 'admin/reports',
    component: AuditReportingComponent,
    canActivate: [AuthGuard, RoleGuard],
    data: { roles: ['ADMIN'] }
  },
  {
    path: 'admin/requests',
    component:RequestManagementComponent,
    canActivate: [AuthGuard, RoleGuard],
    data: { roles: ['ADMIN'] }
  },
  // In your routes file (app.routes.ts)
{
  path: 'admin/assets/edit/:id',
  component: EditAssetComponent, // You'll need to create this component
  canActivate: [AuthGuard, RoleGuard],
  data: { roles: ['ADMIN'] }
},
{
  path: 'admin/assets/add',
  component: AddAssetComponent,
  canActivate: [AuthGuard, RoleGuard],
  data: { roles: ['ADMIN'] }
},
  // Employee routes
  {
  path: 'employee/allocated-assets',
  component: AllocatedAssetsComponent,
  canActivate: [AuthGuard, RoleGuard],
  data: { roles: ['EMPLOYEE'] }
},
  {
    path: 'employee/dashboard',
    component: EmployeeDashboardComponent,
    canActivate: [AuthGuard, RoleGuard],
    data: { roles: ['EMPLOYEE'] }
  },
  {
    path: 'employee/assets',
    component: ViewAssetsComponent,
    canActivate: [AuthGuard, RoleGuard],
    data: { roles: ['EMPLOYEE'] }
  },
  {
    path: 'employee/request-asset',
    component: RequestAssetComponent,
    canActivate: [AuthGuard, RoleGuard],
    data: { roles: ['EMPLOYEE'] }
  },
  

  // Fallback route
  { path: '**', redirectTo: '/login' }
];
