package com.emranhss.GarmentsManagementSystem.dto.response;

import com.emranhss.GarmentsManagementSystem.enums.ProductionStage;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class OrderResponseDto {
    private Long id;

    private String orderId;

    private String poNumber;

    private Long buyerId;

    private String buyerCode;

    private String buyerName;

    private Long styleId;

    private String styleCode;

    private String styleName;

    private LocalDate orderDate;

    private LocalDate shipDate;

    private String status;

    private String shippingAddress;

    private Integer totalQuantity;

    private BigDecimal subtotal;

    private BigDecimal vatPercent;

    private BigDecimal vat;

    private BigDecimal grandTotal;

    private BigDecimal totalAmount;

    private ProductionStage currentStage;

    private List<OrderItemResponseDto> items;
}
