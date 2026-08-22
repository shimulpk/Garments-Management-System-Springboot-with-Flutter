package com.emranhss.GarmentsManagementSystem.dto.response;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class DayWiseFinishingProductionResponseDto {

    private Long id;

    private Long finishingPlanId;

    private String finishingPlanCode;

    private LocalDate date;

    private Integer passQty;

    private Integer rejectQty;

    private String remarks;

    private String styleNo;

    private String buyerName;

    private LocalDateTime createdAt;
}
