package com.emranhss.GarmentsManagementSystem.entity;

import com.emranhss.GarmentsManagementSystem.enums.FinishingPlanStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "finishing_plans")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FinishingPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    Auto Generate Example:FP-1780809791460

    private String finishingPlanId;


//      Only COMPLETED Sewing Plan can create Finishing Plan

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sewing_plan_id",
            nullable = false)
    @JsonIgnore
    private SewingPlan sewingPlan;

    private String buyerName;

    private String orderNo;

    private String styleNo;

    private String color;

//    Auto From Sewing Plan

    private Integer inputQty;

    private Integer targetQty;


//      Auto Update From DayWiseFinishingProduction

    private Integer passQty;


//      Auto Update From DayWiseFinishingProduction

    private Integer rejectionQty;

    private Boolean procTrimming;

    private Boolean procIroning;

    private Boolean procWashing;

    private Boolean procButtonAttach;

    private String finishingTableNo;

    private String supervisorName;

    private LocalDate startDate;

    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FinishingPlanStatus status;
}
