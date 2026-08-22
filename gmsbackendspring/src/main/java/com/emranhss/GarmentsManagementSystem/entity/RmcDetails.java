package com.emranhss.GarmentsManagementSystem.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity
@Table(name = "new_rmc_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RmcDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ==========================
    // Header Relation
    // ==========================

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rmc_check_id", nullable = false)
    @JsonIgnore
    private RmcCheck rmcCheck;

    // ==========================
    // Material Information
    // ==========================

    private String materialName;



    private String unit;

    // BOM Consumption

    private Double qtyPerPiece;

    // BOM Unit Price

    private Double unitPrice;

    // OrderQty × QtyPerPiece

    private Double totalQtyRequired;

    // RequiredQty × UnitPrice

    private Double totalMaterialCost;

}

