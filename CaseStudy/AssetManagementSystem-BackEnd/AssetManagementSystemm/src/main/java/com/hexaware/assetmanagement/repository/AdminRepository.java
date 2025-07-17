package com.hexaware.assetmanagement.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.hexaware.assetmanagement.model.Admin;

public interface AdminRepository extends JpaRepository<Admin, Integer> {
    Optional<Admin> findByUserName(String userName);
    Optional<Admin> findByEmail(String email);
}
