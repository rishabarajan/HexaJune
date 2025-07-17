package com.hexaware.assetmanagement.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "AssetRequest")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AssetRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_id")
    private int requestId;

    // ✅ These will come from frontend
    @Transient
    private Integer employeeId;

    @Transient
    private Integer assetId;

    // ✅ FetchType.LAZY is efficient for large data
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", referencedColumnName = "employee_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Employee employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asset_id", referencedColumnName = "asset_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Asset asset;

    @Column(name = "request_date")
    private String requestDate;

    @Column(name = "status")
    private String status;

    @Column(name = "request_type")
    private String requestType;

    @Column(name = "description")
    private String description;

    @Column(name = "issue_type")
    private String issueType;
}
