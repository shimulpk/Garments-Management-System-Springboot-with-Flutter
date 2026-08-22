package com.emranhss.GarmentsManagementSystem.dto.response;

import lombok.Data;

@Data
public class StockResponseDto {

    private Long id;

    private Long itemId;

    private String itemName;

    private String category;

    private String unit;

    private Double availableQuantity;
}
