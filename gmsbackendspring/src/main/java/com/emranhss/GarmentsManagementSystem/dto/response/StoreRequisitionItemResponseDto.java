package com.emranhss.GarmentsManagementSystem.dto.response;

import lombok.Data;

@Data
public class StoreRequisitionItemResponseDto {

    private Long id;

    private Long itemId;

    private String itemName;
    private String unit;
    private Double quantity;


}
