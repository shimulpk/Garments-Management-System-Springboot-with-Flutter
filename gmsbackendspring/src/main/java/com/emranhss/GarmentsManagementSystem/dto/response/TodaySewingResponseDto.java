package com.emranhss.GarmentsManagementSystem.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class TodaySewingResponseDto {

    private String styleNo;

    private String orderNo;

    private Long productionQty;

    private Long rejectQty;
}
