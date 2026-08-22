package com.emranhss.GarmentsManagementSystem.dto.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class UomRequestDto {
    private String productName;

    private String size;

    private BigDecimal body;

    private BigDecimal sleeve;

    private BigDecimal pocket;

    private BigDecimal wastage;

    private BigDecimal shrinkage;
}
