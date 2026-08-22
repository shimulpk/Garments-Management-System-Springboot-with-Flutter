package com.emranhss.GarmentsManagementSystem.dto.response;

import com.emranhss.GarmentsManagementSystem.enums.CuttingPlanStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class CuttingPlanResponseDto {

    private Long id;

    private String cuttingPlanId;

    private Long buyerId;

    private String buyerCode;

    private String buyerName;

    private Long orderDbId;

    private String orderId;

    private String poNumber;

    private String styleNo;

    private String fabricType;

    private String color;

    private BigDecimal totalFabricRequired;

    private BigDecimal markerLength;

    private BigDecimal markerWidth;

    private Integer numberOfPlies;

    private BigDecimal markerEfficiency;

    private Integer plannedPieces;

    private String cuttingTableNumber;

    private String cuttingMaster;

    private LocalDate startDate;

    private LocalDate endDate;

    private CuttingPlanStatus status;

    private Integer actualCutPieces;

    private Integer rejectedPieces;

    private Integer remainingPieces;

    private Double progress;
}
