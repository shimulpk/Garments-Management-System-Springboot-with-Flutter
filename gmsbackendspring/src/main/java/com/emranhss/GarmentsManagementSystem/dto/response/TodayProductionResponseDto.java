package com.emranhss.GarmentsManagementSystem.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TodayProductionResponseDto {

    private String department;

    private Long productionQty;
}
