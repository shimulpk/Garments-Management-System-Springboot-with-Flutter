package com.emranhss.GarmentsManagementSystem.dto.response;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor

public class DayWiseCuttingHistorySummaryResponseDto {

    private Long totalCutPieces;

    private Long totalRejectPieces;

    private Long totalEntries;

    private LocalDateTime lastUpdated;

    public DayWiseCuttingHistorySummaryResponseDto(
            Long totalCutPieces,
            Long totalRejectPieces,
            Long totalEntries,
            LocalDateTime lastUpdated) {

        this.totalCutPieces = totalCutPieces;
        this.totalRejectPieces = totalRejectPieces;
        this.totalEntries = totalEntries;
        this.lastUpdated = lastUpdated;
    }
}
