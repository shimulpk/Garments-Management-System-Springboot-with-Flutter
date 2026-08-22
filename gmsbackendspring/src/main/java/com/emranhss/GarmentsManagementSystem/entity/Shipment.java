package com.emranhss.GarmentsManagementSystem.entity;

import com.emranhss.GarmentsManagementSystem.enums.ShipmentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "shipments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Shipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Auto Generate
    // SHP-1780811449078
    @Column(nullable = false, unique = true)
    private String shipmentNo;

    // One Packing Plan = One Shipment
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "packing_plan_id", nullable = false, unique = true)
    private PackingPlan packingPlan;

    /*
     * Auto Fill From Packing Plan
     */

    private String buyerName;

    private String destination;

    private String orderNo;

    private String styleNo;



    // Auto From Packing Plan (totalPackedQty)
    private Integer shipmentQty;

    /*
     * User Input
     */

    @Column(nullable = false)
    private LocalDate shipmentDate;

    @Column(length = 1000)
    private String remarks;

    /*
     * Auto Update
     */

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ShipmentStatus status = ShipmentStatus.PENDING;

}
