package com.emranhss.GarmentsManagementSystem.dto.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class UomResponseDto {
    private Long id;

    private String productName;

    private String size;

    private BigDecimal body;

    private BigDecimal sleeve;

    private BigDecimal pocket;

    private BigDecimal wastage;

    private BigDecimal shrinkage;

    private BigDecimal totalBaseFabric;
}
