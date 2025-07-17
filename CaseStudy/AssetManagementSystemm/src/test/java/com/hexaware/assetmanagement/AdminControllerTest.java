package com.hexaware.assetmanagement;

import com.hexaware.assetmanagement.controller.AdminController;
import com.hexaware.assetmanagement.model.Admin;
import com.hexaware.assetmanagement.service.AdminService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class AdminControllerTest {

    private AdminController adminController;

    private FakeAdminService fakeAdminService;

    private Admin sampleAdmin;

    @BeforeEach
    void setUp() {
        fakeAdminService = new FakeAdminService();
        adminController = new AdminController(fakeAdminService);

        sampleAdmin = new Admin(1, "Admin User", "admin123", "pass123", "admin@example.com",
                "9876543210", "ROLE_ADMIN", "Male");

        // Preload one admin
        fakeAdminService.addAdmin(sampleAdmin);
    }

    @Test
    void testAddAdmin_Success() {
        Admin newAdmin = new Admin(2, "Second Admin", "admin456", "pass456", "second@example.com",
                "9876500000", "ROLE_ADMIN", "Female");

        ResponseEntity<String> response = adminController.addAdmin(newAdmin);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals("Admin added successfully", response.getBody());
    }

    @Test
    void testLogin_Success() {
        ResponseEntity<String> response = adminController.login(sampleAdmin);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Login successful", response.getBody());
    }

    @Test
    void testLogin_Failure() {
        Admin wrongAdmin = new Admin();
        wrongAdmin.setUserName("admin123");
        wrongAdmin.setPassword("wrongpass");

        ResponseEntity<String> response = adminController.login(wrongAdmin);
        assertEquals(401, response.getStatusCodeValue());
        assertEquals("Invalid credentials", response.getBody());
    }

    @Test
    void testGetAllAdmins_WithData() {
        ResponseEntity<List<Admin>> response = adminController.getAllAdmins();
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void testGetAllAdmins_Empty() {
        fakeAdminService.clear(); // Empties the list
        ResponseEntity<List<Admin>> response = adminController.getAllAdmins();
        assertEquals(204, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    // --- Fake AdminService Implementation ---
    static class FakeAdminService extends AdminService {
        private final List<Admin> adminList = new ArrayList<>();

        public FakeAdminService() {
            super(null); // Passing null because we won't use the repository
        }

        @Override
        public void addAdmin(Admin admin) {
            adminList.add(admin);
        }

        @Override
        public boolean validateLogin(String username, String password) {
            return adminList.stream()
                    .anyMatch(a -> a.getUserName().equals(username) && a.getPassword().equals(password));
        }

        @Override
        public List<Admin> getAllAdmins() {
            return new ArrayList<>(adminList);
        }

        public void clear() {
            adminList.clear();
        }
    }
}
