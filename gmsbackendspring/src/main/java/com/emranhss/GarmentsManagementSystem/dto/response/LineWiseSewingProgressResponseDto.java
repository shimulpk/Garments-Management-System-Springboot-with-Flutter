package com.emranhss.GarmentsManagementSystem.dto.response;

import lombok.Data;

@Data
public class LineWiseSewingProgressResponseDto {

    private Long productionLineId;

    private String lineId;

    private Integer targetQty;

    private Integer achievedQty;

    private Integer rejectQty;

    private Integer remainingQty;
}
