package com.emranhss.GarmentsManagementSystem.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TodayFinishingResponseDto {

    private String styleNo;

    private String buyerName;

    private Long productionQty;

    private Long rejectQty;
}
