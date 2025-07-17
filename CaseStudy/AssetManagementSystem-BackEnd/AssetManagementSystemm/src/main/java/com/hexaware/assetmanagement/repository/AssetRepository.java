package com.hexaware.assetmanagement.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hexaware.assetmanagement.model.Asset;

@Repository
public interface AssetRepository extends JpaRepository<Asset, Integer> {
    List<Asset> findByAssetCategory(String assetCategory);
}