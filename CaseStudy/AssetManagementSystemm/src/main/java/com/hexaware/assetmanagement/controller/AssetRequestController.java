package com.hexaware.assetmanagement.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.hexaware.assetmanagement.model.AssetRequest;
import com.hexaware.assetmanagement.service.AssetRequestService;

@RestController
@RequestMapping("/api/assetrequests")
public class AssetRequestController {

    @Autowired
    private AssetRequestService assetRequestService;
    
    @PostMapping("/request")
    public String addAssetRequest(@RequestBody AssetRequest assetRequest) {
        return assetRequestService.addAssetRequest(assetRequest);
    }
    
    @PutMapping("/update")
    public String updateAssetRequest(@RequestBody AssetRequest assetRequest) {
        return assetRequestService.updateAssetRequest(assetRequest);
    }
    
    @GetMapping("/all")
    public List<AssetRequest> getAllAssetRequests() {
        return assetRequestService.getAllAssetRequests();
    }
    
    @GetMapping("/byStatus/{status}")
    public List<AssetRequest> getAssetRequestsByStatus(@PathVariable String status) {
        return assetRequestService.getAssetRequestsByStatus(status);
    }
}