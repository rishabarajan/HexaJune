package com.hexaware.assetmanagement.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hexaware.assetmanagement.model.AssetRequest;
import com.hexaware.assetmanagement.repository.AssetRequestRepository;

@Service
public class AssetRequestService {

    @Autowired
    private AssetRequestRepository assetRequestRepository;
    
    public String addAssetRequest(AssetRequest assetRequest) {
        assetRequestRepository.save(assetRequest);
        return "Asset Request Submitted...";
    }
    
    public String updateAssetRequest(AssetRequest assetRequest) {
        assetRequestRepository.save(assetRequest);
        return "Asset Request Updated...";
    }
    
    public List<AssetRequest> getAllAssetRequests() {
        return assetRequestRepository.findAll();
    }
    
    public List<AssetRequest> getAssetRequestsByStatus(String status) {
        return assetRequestRepository.findByStatus(status);
    }

	
}