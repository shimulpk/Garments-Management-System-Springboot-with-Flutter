package com.emranhss.GarmentsManagementSystem.service;

import com.emranhss.GarmentsManagementSystem.dto.request.PurchaseOrderRequestDto;
import com.emranhss.GarmentsManagementSystem.dto.response.PurchaseOrderResponseDto;

import java.util.List;

public interface PurchaseOrderService {

    // Create Purchase Order
    PurchaseOrderResponseDto create(
            PurchaseOrderRequestDto request
    );

    // Update Purchase Order
    PurchaseOrderResponseDto update(
            Long id,
            PurchaseOrderRequestDto request
    );

    // Get Purchase Order By Id
    PurchaseOrderResponseDto getById(
            Long id
    );

    // Get All Purchase Orders
    List<PurchaseOrderResponseDto> getAll();

    // Delete Purchase Order
    void delete(
            Long id
    );

    List<PurchaseOrderResponseDto> getPendingPurchaseOrders();

}
