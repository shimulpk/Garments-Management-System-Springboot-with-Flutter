package com.emranhss.GarmentsManagementSystem.dto.request;

import lombok.Data;

@Data
public class SewingTargetRequestDto {
    private Long productionLineId;

    private Integer targetQuantity;
}
