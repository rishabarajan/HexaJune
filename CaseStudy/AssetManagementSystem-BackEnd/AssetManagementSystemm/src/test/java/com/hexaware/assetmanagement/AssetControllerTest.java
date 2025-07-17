package com.hexaware.assetmanagement;

import com.hexaware.assetmanagement.controller.AssetController;
import com.hexaware.assetmanagement.model.Asset;
import com.hexaware.assetmanagement.service.AssetService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import java.lang.reflect.Field;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class AssetControllerTest {

    private AssetController assetController;
    private FakeAssetService fakeAssetService;
    private Asset sampleAsset;

    @BeforeEach
    void setUp() throws Exception {
        fakeAssetService = new FakeAssetService();
        assetController = new AssetController();

        Field field = AssetController.class.getDeclaredField("assetService");
        field.setAccessible(true);
        field.set(assetController, fakeAssetService);

        sampleAsset = new Asset(
                1, "Laptop", "Electronics", "Dell Inspiron", "2022-01-01",
                "2025-01-01", 55000.00, "Office work laptop", "Available"
        );

        fakeAssetService.addAsset(sampleAsset);
    }

    @Test
    void testAddAsset() {
        Asset newAsset = new Asset(
                2, "Monitor", "Electronics", "LG 24inch", "2022-06-01",
                "2026-06-01", 12000.00, "External monitor", "Available"
        );

        ResponseEntity<Asset> response = assetController.addAsset(newAsset);
        assertEquals(201, response.getStatusCodeValue());
        assertEquals("Monitor", response.getBody().getAssetName());
    }

    @Test
    void testUpdateAsset_Valid() {
        sampleAsset.setAssetValue(60000.00);
        ResponseEntity<Asset> response = assetController.updateAsset(1, sampleAsset, "Bearer token");

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(60000.00, response.getBody().getAssetValue());
    }

    @Test
    void testUpdateAsset_MismatchedId() {
        sampleAsset.setAssetId(2); // Mismatch
        ResponseEntity<Asset> response = assetController.updateAsset(1, sampleAsset, "Bearer token");

        assertEquals(400, response.getStatusCodeValue()); // Bad Request
    }

    @Test
    void testDeleteAsset() {
        ResponseEntity<String> response = assetController.deleteAsset(1);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Asset deleted successfully", response.getBody());
    }

    @Test
    void testGetAllAssets() {
        ResponseEntity<List<Asset>> response = assetController.getAllAssets();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        assertEquals("Laptop", response.getBody().get(0).getAssetName());
    }

    @Test
    void testSearchAssetById_Found() {
        ResponseEntity<Asset> response = assetController.searchAssetById(1);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Laptop", response.getBody().getAssetName());
    }

    @Test
    void testSearchAssetById_NotFound() {
        ResponseEntity<Asset> response = assetController.searchAssetById(999);

        assertEquals(200, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    void testSearchAssetsByCategory() {
        ResponseEntity<List<Asset>> response = assetController.searchAssetsByCategory("Electronics");

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
    }

    // --- Fake AssetService Implementation ---
    static class FakeAssetService extends AssetService {
        private final Map<Integer, Asset> assets = new HashMap<>();

        public FakeAssetService() {
            super();
        }

        @Override
        public Asset addAsset(Asset asset) {
            assets.put(asset.getAssetId(), asset);
            return asset;
        }

        @Override
        public Asset updateAsset(Asset asset) {
            assets.put(asset.getAssetId(), asset);
            return asset;
        }

        @Override
        public String deleteAsset(int assetId) {
            assets.remove(assetId);
            return "Asset deleted successfully";
        }

        @Override
        public List<Asset> getAllAssets() {
            return new ArrayList<>(assets.values());
        }

        @Override
        public Asset searchAssetById(int assetId) {
            return assets.get(assetId);
        }

        @Override
        public List<Asset> searchAssetsByCategory(String category) {
            List<Asset> filtered = new ArrayList<>();
            for (Asset asset : assets.values()) {
                if (asset.getAssetCategory().equalsIgnoreCase(category)) {
                    filtered.add(asset);
                }
            }
            return filtered;
        }
    }
}
