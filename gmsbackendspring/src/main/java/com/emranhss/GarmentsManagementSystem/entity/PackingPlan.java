package com.emranhss.GarmentsManagementSystem.entity;

import com.emranhss.GarmentsManagementSystem.enums.PackingPlanStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "packing_plans")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PackingPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//     Auto Generate   PKG-1780811449078

    private String packingPlanId;

//    Only Completed Finishing Plan can create Packing Plan

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "finishing_plan_id", nullable = false)
    @JsonIgnore
    private FinishingPlan finishingPlan;

//     Auto From Finishing Plan

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    @JsonIgnore
    private Order order;


    private String buyerName;

    private String orderNo;

    private String styleNo;

    private String color;

//    Auto From Order
    private Integer totalOrderQty;

//   Auto From Finishing Plan
    private Integer inputQty;

//     Auto Update From  DayWisePackingProduction
    private Integer totalPackedQty;

//    Auto Update

    private Integer rejectionQty;

//    Solid Packing  Ratio,Assorted


    private String packingMethod;

//    User Input

    private Integer pcsPerCarton;

//     Auto Calculate

    private Integer totalPlannedCartons;

    private String polyBagType;

    private Boolean hangTag;

    private String packingSupervisor;

    private LocalDate startDate;

    private LocalDate expectedShipmentDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PackingPlanStatus status;

}
