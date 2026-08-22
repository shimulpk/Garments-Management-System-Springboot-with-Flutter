package com.emranhss.GarmentsManagementSystem.dto.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BomViewResponseDto {
    private Long id;

    private Integer serial;

    private String materialName;

    private String unit;

    private String baseFabric;

    private Long styleId;

    private String styleCode;

    private String styleName;

    private BigDecimal quantity;

    private BigDecimal unitPrice;

    private BigDecimal totalCost;
}
