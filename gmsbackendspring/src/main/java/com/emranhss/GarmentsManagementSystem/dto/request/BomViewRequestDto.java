package com.emranhss.GarmentsManagementSystem.dto.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BomViewRequestDto {
    private Integer serial;

    private String materialName;

    private String unit;

    private String baseFabric;

    private Long styleId;

    private BigDecimal quantity;

    private BigDecimal unitPrice;
}
