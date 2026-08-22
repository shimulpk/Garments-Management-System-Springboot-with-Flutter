package com.emranhss.GarmentsManagementSystem.dto.request;

import lombok.Data;

@Data
public class ProductionLineRequestDto {
    private String lineId;

    private Integer capacityPerDay;

    private String supervisor;
}
