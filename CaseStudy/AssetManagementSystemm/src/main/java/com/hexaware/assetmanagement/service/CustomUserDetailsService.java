package com.hexaware.assetmanagement.service;

import com.hexaware.assetmanagement.model.Admin;
import com.hexaware.assetmanagement.model.Employee;
import com.hexaware.assetmanagement.repository.AdminRepository;
import com.hexaware.assetmanagement.repository.EmployeeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private AdminRepository adminRepo;

    @Autowired
    private EmployeeRepository employeeRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Admin> adminOpt = adminRepo.findByUserName(username);
        if (adminOpt.isPresent()) {
            Admin admin = adminOpt.get();
            return new User(admin.getUserName(), admin.getPassword(),
                    List.of(new SimpleGrantedAuthority("ROLE_ADMIN")));
        }

        Optional<Employee> empOpt = employeeRepo.findByUserName(username);
        if (empOpt.isPresent()) {
            Employee emp = empOpt.get();
            return new User(emp.getUserName(), emp.getPassword(),
                    List.of(new SimpleGrantedAuthority("ROLE_EMPLOYEE")));
        }

        throw new UsernameNotFoundException("User not found");
    }
}
