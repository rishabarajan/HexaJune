package com.hexaware.assetmanagement.controller;

import java.util.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import com.hexaware.assetmanagement.model.Admin;
import com.hexaware.assetmanagement.service.AdminService;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }
    @PostMapping("/add")
    public ResponseEntity<String> addAdmin(@RequestBody Admin admin) {
        try {
            adminService.addAdmin(admin);
            return ResponseEntity.status(HttpStatus.CREATED).body("Admin added successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error adding admin: " + e.getMessage());
        }
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Admin admin) {
        String username = admin.getUserName();
        String password = admin.getPassword();

        if (adminService.validateLogin(username, password)) {
            return ResponseEntity.ok("Login successful");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }
    @GetMapping("/all")
    public ResponseEntity<List<Admin>> getAllAdmins() {
        List<Admin> admins = adminService.getAllAdmins();
        if (admins.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(admins);
    }
}