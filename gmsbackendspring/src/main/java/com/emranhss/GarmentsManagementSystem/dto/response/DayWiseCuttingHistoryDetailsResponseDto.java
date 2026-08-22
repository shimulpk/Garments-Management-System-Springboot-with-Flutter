package com.emranhss.GarmentsManagementSystem.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class DayWiseCuttingHistoryDetailsResponseDto {

    private Long id;

    private LocalDateTime createdAt;

    private Integer actualCutPieces;

    private Integer rejectPieces;
}
