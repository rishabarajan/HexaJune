package com.hexaware.assetmanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hexaware.assetmanagement.model.Employee;
import com.hexaware.assetmanagement.service.EmployeeService;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    
    @DeleteMapping("/delete/{employeeId}")
    public String deleteEmployee(@PathVariable int employeeId) {
        return employeeService.deleteEmployee(employeeId);
    }
    
    @PostMapping("/add")
    public String addEmployee(@RequestBody Employee employee) {
        return employeeService.addEmployee(employee);
    }
    
    @PutMapping("/update")
    public String updateEmployee(@RequestBody Employee employee) {
        return employeeService.updateEmployee(employee);
    }
    
    @GetMapping("/all")
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }
    
    @GetMapping("/search/{employeeId}")
    public Employee searchEmployeeById(@PathVariable int employeeId) {
        return employeeService.searchEmployeeById(employeeId);
    }
    
    @GetMapping("/searchByUsername/{userName}")
    public Employee searchEmployeeByUserName(@PathVariable String userName) {
        return employeeService.searchEmployeeByUserName(userName);
    }
}