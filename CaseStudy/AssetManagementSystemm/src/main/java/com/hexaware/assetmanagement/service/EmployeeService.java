package com.hexaware.assetmanagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.assetmanagement.model.Employee;
import com.hexaware.assetmanagement.repository.EmployeeRepository;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;
    
    public String deleteEmployee(int employeeId) {
        Employee employee = searchEmployeeById(employeeId);
        employeeRepository.delete(employee);
        return "Employee Record Deleted...";
    }
    
    public String updateEmployee(Employee employee) {
        employeeRepository.save(employee);
        return "Employee Record Updated...";
    }
    
    public String addEmployee(Employee employee) {
        employeeRepository.save(employee);
        return "Employee Record Inserted...";
    }
    
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }
    
    public Employee searchEmployeeById(int employeeId) {
        return employeeRepository.findById(employeeId).get();
    }
    
    public Employee searchEmployeeByUserName(String userName) {
        return employeeRepository.findByUserName(userName);
    }
}