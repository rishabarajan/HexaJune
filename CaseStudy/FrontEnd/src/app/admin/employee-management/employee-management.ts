import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Employee } from '../../classes/employee.model';
import { EmployeeService } from '../../service/employee/employee.service';

@Component({
  selector: 'app-employee-management',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './employee-management.html',
  styleUrls: ['./employee-management.scss']
})
export class EmployeeManagementComponent implements OnInit {
  employees: Employee[] = [];
  filteredEmployees: Employee[] = [];
  searchTerm: string = '';

  constructor(private employeeService: EmployeeService) {}

  ngOnInit(): void {
    this.loadEmployees();
  }

  loadEmployees(): void {
    this.employeeService.getAllEmployees().subscribe({
      next: (data: any[]) => {
        // Transform API data to match our Employee model
        this.employees = data.map(emp => ({
          employee_id: emp.employeeId,
          employee_name: emp.employeeName,
          user_name: emp.userName,
          email: emp.email,
          // Include other properties if needed
          password: emp.password,
          contact_number: emp.contactNumber, // adjust if needed
          address: emp.address,
          role: emp.role,
          join_date: emp.joinDate,
          gender: emp.gender
        }));
        this.filteredEmployees = [...this.employees];
      },
      error: (err) => console.error('Error loading employees:', err)
    });
  }

  searchEmployees(): void {
    this.filteredEmployees = this.employees.filter(emp => 
      emp.employee_name.toLowerCase().includes(this.searchTerm.toLowerCase()) ||
      emp.user_name.toLowerCase().includes(this.searchTerm.toLowerCase()) ||
      emp.email.toLowerCase().includes(this.searchTerm.toLowerCase())
    );
  }
}