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
        assetRepository.deleteById(assetId);
        return "Asset deleted successfully";
    }
    
    public Asset updateAsset(Asset asset) {
        return assetRepository.save(asset);
    }
    
    public Asset addAsset(Asset asset) {
        return assetRepository.save(asset);
    }
    
    public List<Asset> getAllAssets() {
        return assetRepository.findAll();
    }
    
    public Asset searchAssetById(int assetId) {
        return assetRepository.findById(assetId).orElse(null);
    }
    
    public List<Asset> searchAssetsByCategory(String category) {
        return assetRepository.findByAssetCategory(category);
    }
}