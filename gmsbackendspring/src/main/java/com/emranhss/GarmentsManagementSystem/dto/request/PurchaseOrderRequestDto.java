package com.emranhss.GarmentsManagementSystem.dto.request;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class PurchaseOrderRequestDto {

    private LocalDate poDate;

    private Long vendorId;

    private Long storeRequisitionId;

    private String remarks;

    private List<PurchaseOrderItemRequestDto> items;
}
