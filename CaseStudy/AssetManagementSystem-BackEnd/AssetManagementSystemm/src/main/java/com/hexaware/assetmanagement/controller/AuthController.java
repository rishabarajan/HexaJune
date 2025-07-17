package com.hexaware.assetmanagement.controller;

import com.hexaware.assetmanagement.model.Admin;
import com.hexaware.assetmanagement.model.Employee;
import com.hexaware.assetmanagement.payload.AuthRequest;
import com.hexaware.assetmanagement.payload.AuthResponse;
import com.hexaware.assetmanagement.repository.AdminRepository;
import com.hexaware.assetmanagement.repository.EmployeeRepository;
import com.hexaware.assetmanagement.security.JwtUtil;
import com.hexaware.assetmanagement.service.CustomUserDetailsService;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // -------------------- Register Admin --------------------
    @PostMapping("/register/admin")
    public ResponseEntity<?> registerAdmin(@RequestBody Admin admin) {
        if (adminRepository.findByUserName(admin.getUserName()).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username already exists");
        }
        if (adminRepository.findByEmail(admin.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email already exists");
        }

        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        admin.setRole("Admin");
        adminRepository.save(admin);

        return ResponseEntity.ok("Admin registered successfully");
    }

    // -------------------- Register Employee --------------------
    @PostMapping("/register/employee")
    public ResponseEntity<?> registerEmployee(@RequestBody Employee employee) {
        if (employeeRepository.findByUserName(employee.getUserName()).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username already exists");
        }

        employee.setPassword(passwordEncoder.encode(employee.getPassword()));
        employee.setRole("Employee");
        employeeRepository.save(employee);

        return ResponseEntity.ok("Employee registered");
    }

    // -------------------- Login for Both --------------------
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    request.getUserName(), 
                    request.getPassword()
                )
            );

            UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUserName());
            String role = userDetails.getAuthorities().iterator().next().getAuthority().replace("ROLE_", "");
            String token = jwtUtil.generateToken(userDetails.getUsername(), role);

           
            Long userId = null;

            String name = null;

            if ("ADMIN".equalsIgnoreCase(role)) {
                Admin admin = adminRepository.findByUserName(request.getUserName()).orElse(null);
                if (admin != null) {
                    userId = (long) admin.getAdminId();
                    name = admin.getAdminName(); 
                }
            } else if ("EMPLOYEE".equalsIgnoreCase(role)) {
                Employee employee = employeeRepository.findByUserName(request.getUserName()).orElse(null);
                if (employee != null) {
                    userId = (long) employee.getEmployeeId();
                    name = employee.getEmployeeName(); 
                }
            }

            AuthResponse response = new AuthResponse(token, role, userId, name);
            return ResponseEntity.ok(response);


            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of(
                        "error", "Invalid credentials",
                        "message", "Authentication failed"
                    ));
        }
    
    }
}
