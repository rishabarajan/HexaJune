package com.hexaware.assetmanagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.assetmanagement.model.Asset;
import com.hexaware.assetmanagement.model.AssetRequest;
import com.hexaware.assetmanagement.model.Employee;
import com.hexaware.assetmanagement.repository.AssetRepository;
import com.hexaware.assetmanagement.repository.AssetRequestRepository;
import com.hexaware.assetmanagement.repository.EmployeeRepository;

@Service
public class AssetRequestService {

    @Autowired
    private AssetRequestRepository assetRequestRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private AssetRepository assetRepository;

    public String addAssetRequest(AssetRequest assetRequest) {
        if (assetRequest.getEmployeeId() != null) {
            Employee employee = employeeRepository.findById(assetRequest.getEmployeeId()).orElse(null);
            assetRequest.setEmployee(employee);
        }

        if (assetRequest.getAssetId() != null) {
            Asset asset = assetRepository.findById(assetRequest.getAssetId()).orElse(null);
            assetRequest.setAsset(asset);
        }

        assetRequestRepository.save(assetRequest);
        return "Asset Request Submitted...";
    }

    public String updateAssetRequest(AssetRequest assetRequest) {
        if (assetRequest.getEmployeeId() != null) {
            Employee employee = employeeRepository.findById(assetRequest.getEmployeeId()).orElse(null);
            assetRequest.setEmployee(employee);
        }

        if (assetRequest.getAssetId() != null) {
            Asset asset = assetRepository.findById(assetRequest.getAssetId()).orElse(null);
            assetRequest.setAsset(asset);
        }

        assetRequestRepository.save(assetRequest);
        return "Asset Request Updated...";
    }

    public AssetRequest getRequestById(int requestId) {
        return assetRequestRepository.findById(requestId).orElse(null);
    }

    public List<AssetRequest> getAllAssetRequests() {
        return assetRequestRepository.findAll();
    }

    public String deleteAssetRequest(int requestId) {
        assetRequestRepository.deleteById(requestId);
        return "Asset Request Deleted Successfully";
    }
    public List<AssetRequest> getApprovedRequestsByEmployeeId(int employeeId) {
        return assetRequestRepository.findByEmployee_EmployeeIdAndStatus(employeeId, "APPROVED");
    }

    public List<AssetRequest> getAssetRequestsByStatus(String status) {
        return assetRequestRepository.findByStatus(status);
    }
}
