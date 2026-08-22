package com.emranhss.GarmentsManagementSystem.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DayWiseCuttingHistoryResponseDto {

    private Long cuttingPlanId;

    private String cuttingPlanCode;

    private String styleNo;

    private LocalDate date;

    private Long totalCutPieces;

    private Long totalRejectPieces;

    private Long totalEntries;

}
