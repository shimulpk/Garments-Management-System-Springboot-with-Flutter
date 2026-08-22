package com.emranhss.GarmentsManagementSystem.dto.request;

import com.emranhss.GarmentsManagementSystem.enums.CuttingPlanStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class CuttingPlanRequestDto {

    private Long buyerId;

    private Long orderId;

    private String fabricType;

    private String color;

    private BigDecimal markerLength;

    private BigDecimal markerWidth;

    private Integer numberOfPlies;

    private BigDecimal markerEfficiency;

    private String cuttingTableNumber;

    private String cuttingMaster;

    private LocalDate startDate;

    private LocalDate endDate;
}
