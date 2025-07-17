package com.hexaware.assetmanagement.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
    public ResponseEntity<Map<String, String>> addAssetRequest(@RequestBody AssetRequest assetRequest) {
        try {
            String result = assetRequestService.addAssetRequest(assetRequest);

            return ResponseEntity.status(HttpStatus.CREATED)
                                 .body(Map.of("message", result));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body(Map.of("message", "Failed to submit request: " + e.getMessage()));
        }
    }

    @GetMapping("/approved/employee/{employeeId}")
    public List<AssetRequest> getApprovedRequestsByEmployee(@PathVariable int employeeId) {
        return assetRequestService.getApprovedRequestsByEmployeeId(employeeId);
    }

    @PutMapping("/update")
    public ResponseEntity<Map<String, String>> updateAssetRequest(@RequestBody AssetRequest assetRequest) {
        try {
            String result = assetRequestService.updateAssetRequest(assetRequest);
            return new ResponseEntity<>(Map.of("message", result), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("message", "Failed to update request: " + e.getMessage()),
                                         HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update-status/{requestId}")
    public ResponseEntity<Map<String, String>> updateRequestStatus(
            @PathVariable int requestId,
            @RequestBody Map<String, String> statusRequest) {

        String status = statusRequest.get("status");
        AssetRequest request = assetRequestService.getRequestById(requestId);
        if (request == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "Request not found"));
        }

        request.setStatus(status);
        assetRequestService.updateAssetRequest(request);

        return ResponseEntity.ok(Map.of("message", "Request status updated to " + status));
    }
    @GetMapping("/all")
    public ResponseEntity<List<AssetRequest>> getAllAssetRequests() {
        try {
            List<AssetRequest> requests = assetRequestService.getAllAssetRequests();
            if (requests.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(requests, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/byStatus/{status}")
    public ResponseEntity<List<AssetRequest>> getAssetRequestsByStatus(@PathVariable String status) {
        try {
            List<AssetRequest> requests = assetRequestService.getAssetRequestsByStatus(status);
            if (requests.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(requests, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/{requestId}")
    public ResponseEntity<AssetRequest> getRequestById(@PathVariable int requestId) {
        AssetRequest request = assetRequestService.getRequestById(requestId);
        return ResponseEntity.ok(request);
    }
    
    @DeleteMapping("/delete/{requestId}")
    public ResponseEntity<String> deleteAssetRequest(@PathVariable int requestId) {
        try {
            String result = "Asset Request Deleted"; 
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to delete request: " + e.getMessage(), 
                                      HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}