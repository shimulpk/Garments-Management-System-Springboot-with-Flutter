package com.emranhss.GarmentsManagementSystem.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TodayCuttingResponseDto {

    private String styleNo;

    private String cuttingMaster;

    private Long productionQty;

    private Long rejectQty;
}
