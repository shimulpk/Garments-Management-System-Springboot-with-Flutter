package com.emranhss.GarmentsManagementSystem.dto.response;

import lombok.Data;

@Data
public class PurchaseOrderItemResponseDto {

    private Long id;

    private Long itemId;

    private String itemName;

    private String unit;

    private Double quantity;

    private Double unitPrice;

    private Double lineTotal;


}
