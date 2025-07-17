
package com.hexaware.assetmanagement.service;

import java.util.*;

import org.springframework.stereotype.Service;

import com.hexaware.assetmanagement.model.Admin;
import com.hexaware.assetmanagement.repository.AdminRepository;
@Service
public class AdminService {
    
    private AdminRepository adminRepo;

    public AdminService(AdminRepository adminRepo) {
        this.adminRepo = adminRepo;
    }

    public void addAdmin(Admin admin) {
        adminRepo.save(admin);
    }

    public boolean validateLogin(String username, String password) {
        Optional<Admin> admin = adminRepo.findByUserName(username);
        return admin.isPresent() && admin.get().getPassword().equals(password);
    }

    public List<Admin> getAllAdmins() {
        return adminRepo.findAll();
    }
}
