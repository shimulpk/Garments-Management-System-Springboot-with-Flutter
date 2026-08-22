package com.emranhss.GarmentsManagementSystem.dto.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemResponseDto {
    private Long id;

    private String type;

    private String size;

    private String color;

    private Integer quantity;

    private BigDecimal unitPrice;

    private BigDecimal lineTotal;
}
