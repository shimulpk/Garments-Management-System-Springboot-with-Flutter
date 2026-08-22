package com.emranhss.GarmentsManagementSystem.dto.request;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class OrderRequestDto {
    private String orderId;

    private String poNumber;

    private Long buyerId;

    private Long styleId;

    private LocalDate orderDate;

    private LocalDate shipDate;

    private String status;

    private String shippingAddress;

    private BigDecimal vatPercent;

    private List<OrderItemRequestDto> items;
}
