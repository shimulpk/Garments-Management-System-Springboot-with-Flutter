package com.emranhss.GarmentsManagementSystem.entity;

import com.emranhss.GarmentsManagementSystem.enums.CuttingPlanStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "cutting_plans")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CuttingPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cuttingPlanId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_id", nullable = false)
    @JsonIgnore
    private Buyer buyer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    @JsonIgnore
    private Order order;

    // Auto from Order
    private String styleNo;

    // User input
    private String fabricType;

    private String color;

    // Auto from RawMaterialCheck
    private BigDecimal totalFabricRequired;

    private BigDecimal markerLength;

    private BigDecimal markerWidth;

    private Integer numberOfPlies;

    private BigDecimal markerEfficiency;

    // Auto from Order.totalQuantity
    private Integer plannedPieces;

    private String cuttingTableNumber;

    private String cuttingMaster;

    private LocalDate startDate;

    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CuttingPlanStatus status;
}
