package com.emranhss.GarmentsManagementSystem.controller;

import com.emranhss.GarmentsManagementSystem.dto.request.PurchaseOrderRequestDto;
import com.emranhss.GarmentsManagementSystem.dto.response.PurchaseOrderResponseDto;
import com.emranhss.GarmentsManagementSystem.service.PurchaseOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/purchase-orders")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN','PURCHASE_MANAGER','STORE_MANAGER')")
public class PurchaseOrderController {

    private final PurchaseOrderService purchaseOrderService;

    // =====================================
    // Create Purchase Order
    // =====================================

    @PostMapping
    public ResponseEntity<PurchaseOrderResponseDto> create(
            @RequestBody PurchaseOrderRequestDto request) {

        return ResponseEntity.ok(
                purchaseOrderService.create(request));
    }

    // =====================================
    // Update Purchase Order
    // =====================================

    @PutMapping("/{id}")
    public ResponseEntity<PurchaseOrderResponseDto> update(
            @PathVariable Long id,
            @RequestBody PurchaseOrderRequestDto request) {

        return ResponseEntity.ok(
                purchaseOrderService.update(id, request));
    }

    // =====================================
    // Get Purchase Order By Id
    // =====================================

    @GetMapping("/{id}")
    public ResponseEntity<PurchaseOrderResponseDto> getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                purchaseOrderService.getById(id));
    }

    // =====================================
    // Get All Purchase Orders
    // =====================================

    @GetMapping
    public ResponseEntity<List<PurchaseOrderResponseDto>> getAll() {

        return ResponseEntity.ok(
                purchaseOrderService.getAll());
    }

    // =====================================
    // Delete Purchase Order
    // =====================================

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(
            @PathVariable Long id) {

        purchaseOrderService.delete(id);

        return ResponseEntity.ok(
                "Purchase Order Deleted Successfully");
    }


    @GetMapping("/pending")
    public List<PurchaseOrderResponseDto> getPendingPurchaseOrders() {

        return purchaseOrderService.getPendingPurchaseOrders();

    }

}
