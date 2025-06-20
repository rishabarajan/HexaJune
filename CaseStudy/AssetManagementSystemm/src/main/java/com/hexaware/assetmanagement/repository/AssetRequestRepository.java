package com.hexaware.assetmanagement.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.hexaware.assetmanagement.model.AssetRequest;

@Repository
public interface AssetRequestRepository extends JpaRepository<AssetRequest, Integer> {
    List<AssetRequest> findAll();
    List<AssetRequest> findByStatus(String status);
}