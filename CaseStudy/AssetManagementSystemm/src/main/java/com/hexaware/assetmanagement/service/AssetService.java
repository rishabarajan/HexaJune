package com.hexaware.assetmanagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.assetmanagement.model.Asset;
import com.hexaware.assetmanagement.repository.AssetRepository;

@Service
public class AssetService {

    @Autowired
    private AssetRepository assetRepository;
    
    public String deleteAsset(int assetId) {
        Asset asset = searchAssetById(assetId);
        assetRepository.delete(asset);
        return "Asset Record Deleted...";
    }
    
    public String updateAsset(Asset asset) {
        assetRepository.save(asset);
        return "Asset Record Updated...";
    }
    
    public String addAsset(Asset asset) {
        assetRepository.save(asset);
        return "Asset Record Inserted...";
    }
    
    public List<Asset> getAllAssets() {
        return assetRepository.findAll();
    }
    
    public Asset searchAssetById(int assetId) {
        return assetRepository.findById(assetId).get();
    }
    
    public List<Asset> searchAssetsByCategory(String category) {
        return assetRepository.findByAssetCategory(category);
    }
}