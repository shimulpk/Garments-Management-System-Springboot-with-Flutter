package com.emranhss.GarmentsManagementSystem.dto.response;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class DayWiseCuttingProductionResponseDto {

    private Long id;

    private Long cuttingPlanId;

    private String cuttingPlanCode;

    private LocalDate date;

    private Integer actualCutPieces;

    private Integer rejectPieces;

    private String styleNo;

    private String cuttingMaster;

    private LocalDateTime createdAt;
}
