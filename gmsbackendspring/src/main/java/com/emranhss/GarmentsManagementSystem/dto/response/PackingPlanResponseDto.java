package com.emranhss.GarmentsManagementSystem.dto.response;

import com.emranhss.GarmentsManagementSystem.enums.PackingPlanStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PackingPlanResponseDto {
    private Long id;

    private String packingPlanId;

    private Long finishingPlanId;

    private String finishingPlanCode;

    private Long orderId;

    private String orderNo;


    private String buyerName;

    private String styleNo;

    private String color;

    private Integer totalOrderQty;

    private Integer inputQty;

    private Integer totalPackedQty;

    private Integer rejectionQty;

    private String packingMethod;

    private Integer pcsPerCarton;

    private Integer totalPlannedCartons;

    private String polyBagType;

    private Boolean hangTag;

    private String packingSupervisor;

    private LocalDate startDate;

    private LocalDate expectedShipmentDate;

    private PackingPlanStatus status;

    private Integer packedSoFar;

    private Integer remaining;

    private Double progressPercentage;

    private String destination;

}
