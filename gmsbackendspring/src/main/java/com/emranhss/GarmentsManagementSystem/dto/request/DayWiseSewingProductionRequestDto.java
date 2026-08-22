package com.emranhss.GarmentsManagementSystem.dto.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class DayWiseSewingProductionRequestDto {
    private Long sewingPlanId;

    private Long productionLineId;

    private LocalDate date;

    private Integer achievedQuantity;

    private Integer rejectionQty;
}
