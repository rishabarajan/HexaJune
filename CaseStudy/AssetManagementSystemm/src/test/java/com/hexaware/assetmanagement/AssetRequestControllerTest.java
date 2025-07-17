package com.hexaware.assetmanagement;

import com.hexaware.assetmanagement.controller.AssetRequestController;
import com.hexaware.assetmanagement.model.Asset;
import com.hexaware.assetmanagement.model.AssetRequest;
import com.hexaware.assetmanagement.model.Employee;
import com.hexaware.assetmanagement.service.AssetRequestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.lang.reflect.Field;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class AssetRequestControllerTest {

    private AssetRequestController controller;

    private final List<AssetRequest> inMemoryRequests = new ArrayList<>();

    @BeforeEach
    void setUp() throws Exception {
        controller = new AssetRequestController();

        // Fake service implementing the exact logic structure
        AssetRequestService fakeService = new AssetRequestService() {
            private int currentId = 1;

            @Override
            public String addAssetRequest(AssetRequest request) {
                request.setRequestId(currentId++);
                request.setEmployee(new Employee()); // Fake association
                request.setAsset(new Asset());       // Fake association
                inMemoryRequests.add(request);
                return "Asset Request Submitted...";
            }

            @Override
            public String updateAssetRequest(AssetRequest request) {
                for (int i = 0; i < inMemoryRequests.size(); i++) {
                    if (inMemoryRequests.get(i).getRequestId() == request.getRequestId()) {
                        inMemoryRequests.set(i, request);
                        return "Asset Request Updated...";
                    }
                }
                return "Not Found";
            }

            @Override
            public AssetRequest getRequestById(int id) {
                return inMemoryRequests.stream()
                        .filter(r -> r.getRequestId() == id)
                        .findFirst()
                        .orElse(null);
            }

            @Override
            public List<AssetRequest> getAllAssetRequests() {
                return new ArrayList<>(inMemoryRequests);
            }

            @Override
            public String deleteAssetRequest(int id) {
                return inMemoryRequests.removeIf(r -> r.getRequestId() == id)
                        ? "Asset Request Deleted Successfully"
                        : "Not Found";
            }

            @Override
            public List<AssetRequest> getApprovedRequestsByEmployeeId(int empId) {
                List<AssetRequest> result = new ArrayList<>();
                for (AssetRequest r : inMemoryRequests) {
                    if ("APPROVED".equalsIgnoreCase(r.getStatus())) {
                        result.add(r);
                    }
                }
                return result;
            }

            @Override
            public List<AssetRequest> getAssetRequestsByStatus(String status) {
                List<AssetRequest> result = new ArrayList<>();
                for (AssetRequest r : inMemoryRequests) {
                    if (status.equalsIgnoreCase(r.getStatus())) {
                        result.add(r);
                    }
                }
                return result;
            }
        };

        // Inject using reflection
        Field field = AssetRequestController.class.getDeclaredField("assetRequestService");
        field.setAccessible(true);
        field.set(controller, fakeService);
    }

    @Test
    void testAddAssetRequest() {
        AssetRequest request = new AssetRequest();
        request.setEmployeeId(1);
        request.setAssetId(10);
        request.setStatus("PENDING");

        var response = controller.addAssetRequest(request);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals("Asset Request Submitted...", response.getBody().get("message"));
    }

    @Test
    void testGetAllAssetRequests() {
        testAddAssetRequest(); // Add 1 request

        ResponseEntity<List<AssetRequest>> response = controller.getAllAssetRequests();
        assertEquals(200, response.getStatusCodeValue());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    void testGetRequestById() {
        testAddAssetRequest();
        int id = controller.getAllAssetRequests().getBody().get(0).getRequestId();

        ResponseEntity<AssetRequest> response = controller.getRequestById(id);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(id, response.getBody().getRequestId());
    }

    @Test
    void testUpdateAssetRequest() {
        testAddAssetRequest();
        AssetRequest original = controller.getAllAssetRequests().getBody().get(0);
        original.setStatus("APPROVED");

        ResponseEntity<Map<String, String>> response = controller.updateAssetRequest(original);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Asset Request Updated...", response.getBody().get("message"));
    }

    @Test
    void testUpdateRequestStatus() {
        testAddAssetRequest();
        int id = controller.getAllAssetRequests().getBody().get(0).getRequestId();

        var response = controller.updateRequestStatus(id, Map.of("status", "REJECTED"));
        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().get("message").contains("REJECTED"));
    }

    @Test
    void testDeleteAssetRequest() {
        testAddAssetRequest();
        int id = controller.getAllAssetRequests().getBody().get(0).getRequestId();

        ResponseEntity<String> response = controller.deleteAssetRequest(id);
        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().contains("Deleted"));
    }

    @Test
    void testGetAssetRequestsByStatus() {
        AssetRequest req1 = new AssetRequest();
        req1.setEmployeeId(2);
        req1.setAssetId(3);
        req1.setStatus("APPROVED");
        controller.addAssetRequest(req1);

        ResponseEntity<List<AssetRequest>> response = controller.getAssetRequestsByStatus("APPROVED");
        assertEquals(200, response.getStatusCodeValue());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    void testGetApprovedRequestsByEmployee() {
        AssetRequest req1 = new AssetRequest();
        req1.setEmployeeId(2);
        req1.setAssetId(3);
        req1.setStatus("APPROVED");
        controller.addAssetRequest(req1);

        List<AssetRequest> result = controller.getApprovedRequestsByEmployee(2);
        assertEquals(1, result.size());
    }
}
