package com.emranhss.GarmentsManagementSystem.dto.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemRequestDto {
    private String type;

    private String size;

    private String color;

    private Integer quantity;

    private BigDecimal unitPrice;
}
