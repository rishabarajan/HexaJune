package com.hexaware.assetmanagement.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="Asset")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Asset {

    @Id
    @Column(name="asset_id")
    private int assetId;
    
    @Column(name="asset_name", nullable=false)
    private String assetName;
    
    @Column(name="asset_category", nullable=false)
    private String assetCategory;
    
    @Column(name="asset_model")
    private String assetModel;
    
    @Column(name="manufacturing_date")
    private String manufacturingDate;
    
    @Column(name="expiry_date")
    private String expiryDate;
    
    @Column(name="asset_value")
    private double assetValue;
    
    @Column(name="status")
    private String status;
}