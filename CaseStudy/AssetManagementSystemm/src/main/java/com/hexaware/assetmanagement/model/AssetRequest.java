package com.hexaware.assetmanagement.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="AssetRequest")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AssetRequest {

    @Id
    @Column(name="request_id")
    private int requestId;
    
    @ManyToOne
    @JoinColumn(name="employee_id")
    private Employee employee;
    
    @ManyToOne
    @JoinColumn(name="asset_id")
    private Asset asset;
    
    @Column(name="request_date")
    private String requestDate;
    
    @Column(name="status")
    private String status;
    
    @Column(name="request_type")
    private String requestType;
    
    @Column(name="description")
    private String description;
    
    @Column(name="issue_type")
    private String issueType;
}