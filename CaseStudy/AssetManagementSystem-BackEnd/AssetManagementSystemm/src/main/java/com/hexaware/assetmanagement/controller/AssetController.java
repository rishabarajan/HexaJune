package com.hexaware.assetmanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hexaware.assetmanagement.model.Asset;
import com.hexaware.assetmanagement.service.AssetService;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/assets")
public class AssetController {

    @Autowired
    private AssetService assetService;
    
    @DeleteMapping("/delete/{assetId}")
    public ResponseEntity<String> deleteAsset(@PathVariable int assetId) {
        String result = assetService.deleteAsset(assetId);
        return ResponseEntity.ok(result);
    }
    
    @PostMapping("/add")
    public ResponseEntity<Asset> addAsset(@RequestBody Asset asset) {
        Asset savedAsset = assetService.addAsset(asset);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAsset);
    }
    
    @PutMapping("/update/{assetId}")
    public ResponseEntity<Asset> updateAsset(
        @PathVariable int assetId,
        @RequestBody Asset asset,
        @RequestHeader("Authorization") String authHeader
    ) {
        // Verify the path variable matches the asset ID in the body
        if (assetId != asset.getAssetId()) {
            return ResponseEntity.badRequest().build();
        }
        
        Asset updatedAsset = assetService.updateAsset(asset);
        return ResponseEntity.ok(updatedAsset);
    }
    
    @GetMapping("/all")
    public ResponseEntity<List<Asset>> getAllAssets() {
        List<Asset> assets = assetService.getAllAssets();
        return ResponseEntity.ok(assets);
    }
    
    @GetMapping("/search/{assetId}")
    public ResponseEntity<Asset> searchAssetById(@PathVariable int assetId) {
        Asset asset = assetService.searchAssetById(assetId);
        return ResponseEntity.ok(asset);
    }
    
    @GetMapping("/searchByCategory/{category}")
    public ResponseEntity<List<Asset>> searchAssetsByCategory(@PathVariable String category) {
        List<Asset> assets = assetService.searchAssetsByCategory(category);
        return ResponseEntity.ok(assets);
    }
}