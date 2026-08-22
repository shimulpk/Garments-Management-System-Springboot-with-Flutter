package com.emranhss.GarmentsManagementSystem.dto.response;

import lombok.Data;

@Data
public class SewingLineProgressResponseDto {

    private Long productionLineId;

    private String lineId;

    // Target of this line
    private Integer targetQty;

    // Total achieved by this line
    private Integer achievedQty;

    // Total reject by this line
    private Integer rejectQty;

    // Target - Achieved
    private Integer remainingQty;

}
