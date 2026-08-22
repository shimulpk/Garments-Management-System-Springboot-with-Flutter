package com.emranhss.GarmentsManagementSystem.dto.response;

import com.emranhss.GarmentsManagementSystem.enums.PurchaseOrderStatus;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class PurchaseOrderResponseDto {

    private Long id;

    private String poNo;

    private LocalDate poDate;

    private Long vendorId;

    private String vendorName;

    private Long storeRequisitionId;

    private String requisitionNo;

    private PurchaseOrderStatus status;

    private Double grandTotal;

    private String remarks;

    private List<PurchaseOrderItemResponseDto> items;
}
