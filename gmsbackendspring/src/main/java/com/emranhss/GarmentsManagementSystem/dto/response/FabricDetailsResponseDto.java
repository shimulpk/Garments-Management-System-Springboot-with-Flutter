package com.emranhss.GarmentsManagementSystem.dto.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class FabricDetailsResponseDto {
    private Long id;

    private String productName;

    private String size;

    private String type;

    private BigDecimal baseFabric;

    private Integer qty;

    private BigDecimal calculatedFabric;

    private Boolean hasUom;
}
