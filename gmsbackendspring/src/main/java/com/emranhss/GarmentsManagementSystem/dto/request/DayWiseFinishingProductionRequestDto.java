package com.emranhss.GarmentsManagementSystem.dto.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class DayWiseFinishingProductionRequestDto {

    private Long finishingPlanId;

    private LocalDate date;

    private Integer passQty;

    private Integer rejectQty;

    private String remarks;
}
