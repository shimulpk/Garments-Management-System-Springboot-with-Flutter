package com.emranhss.GarmentsManagementSystem.controller;

import com.emranhss.GarmentsManagementSystem.dto.request.ShipmentRequestDto;
import com.emranhss.GarmentsManagementSystem.dto.response.PackingPlanResponseDto;
import com.emranhss.GarmentsManagementSystem.dto.response.ShipmentResponseDto;
import com.emranhss.GarmentsManagementSystem.service.ShipmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shipments")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN','SHIPMENT_EXECUTIVE','PRODUCTION_MANAGER')")
public class ShipmentController {

    private final ShipmentService shipmentService;


    @PostMapping
    public ResponseEntity<ShipmentResponseDto> create(
            @RequestBody ShipmentRequestDto requestDto) {

        return ResponseEntity.ok(
                shipmentService.create(requestDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ShipmentResponseDto> update(
            @PathVariable Long id,
            @RequestBody ShipmentRequestDto requestDto) {

        return ResponseEntity.ok(
                shipmentService.update(id, requestDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShipmentResponseDto> getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                shipmentService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<ShipmentResponseDto>> getAll() {

        return ResponseEntity.ok(
                shipmentService.getAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id) {

        shipmentService.delete(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/available-packing-plans")
    public ResponseEntity<List<PackingPlanResponseDto>> getAvailablePackingPlans() {

        return ResponseEntity.ok(
                shipmentService.getAvailablePackingPlans());
    }

    @PutMapping("/{id}/ship")
    public ResponseEntity<ShipmentResponseDto> markAsShipped(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                shipmentService.markAsShipped(id));
    }


}
