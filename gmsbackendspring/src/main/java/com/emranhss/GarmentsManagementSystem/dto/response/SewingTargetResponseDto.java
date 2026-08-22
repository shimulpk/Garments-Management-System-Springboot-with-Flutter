package com.emranhss.GarmentsManagementSystem.dto.response;

import lombok.Data;

@Data
public class SewingTargetResponseDto {
    private Long id;

    private Long productionLineId;

    private String lineId;

    private Integer targetQuantity;
}
