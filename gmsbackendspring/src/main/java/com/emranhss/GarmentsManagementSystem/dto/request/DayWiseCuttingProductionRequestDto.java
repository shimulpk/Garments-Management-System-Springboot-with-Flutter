package com.emranhss.GarmentsManagementSystem.dto.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class DayWiseCuttingProductionRequestDto {

    private Long cuttingPlanId;

    private LocalDate date;

    private Integer actualCutPieces;

    private Integer rejectPieces;
}
