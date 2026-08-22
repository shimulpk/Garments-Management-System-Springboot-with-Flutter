package com.emranhss.GarmentsManagementSystem.dto.response;

import lombok.Data;

@Data
public class ProductionLineResponseDto {
    private Long id;

    private String lineId;

    private Integer capacityPerDay;

    private String supervisor;

    private Integer machineCount;
}
