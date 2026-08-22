package com.emranhss.GarmentsManagementSystem.dto.response;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class DayWiseSewingProductionResponseDto {
    private Long id;

    private Long sewingPlanId;

    private String sewingPlanCode;

    private Long productionLineId;

    private String lineId;

    private LocalDate date;

    private Integer achievedQuantity;

    private Integer rejectionQty;

    private String styleNo;

    private String orderNo;

    private LocalDateTime createdAt;
}
