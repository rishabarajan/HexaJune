package com.hexaware.assetmanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import com.hexaware.assetmanagement.model.AssetRequest;

@Repository
public interface AssetRequestRepository extends JpaRepository<AssetRequest, Integer> {

    // Existing methods
    List<AssetRequest> findByStatus(String status);
    List<AssetRequest> findByEmployee_EmployeeIdAndStatus(int employeeId, String status);

    // ✅ New method to fetch employee and asset details
    @Query("SELECT ar FROM AssetRequest ar " +
           "JOIN FETCH ar.employee " +
           "JOIN FETCH ar.asset")
    List<AssetRequest> findAllWithDetails();
}
