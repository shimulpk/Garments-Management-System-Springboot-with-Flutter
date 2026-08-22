package com.emranhss.GarmentsManagementSystem.controller;

import com.emranhss.GarmentsManagementSystem.dto.request.OrderRequestDto;
import com.emranhss.GarmentsManagementSystem.dto.response.OrderResponseDto;
import com.emranhss.GarmentsManagementSystem.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN','MERCHANDISER','CUTTING_MANAGER')")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponseDto> create(
            @RequestBody OrderRequestDto request) {

        return ResponseEntity.ok(
                orderService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderResponseDto> update(
            @PathVariable Long id,
            @RequestBody OrderRequestDto request) {

        return ResponseEntity.ok(
                orderService.update(id, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDto> getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                orderService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<OrderResponseDto>> getAll() {

        return ResponseEntity.ok(
                orderService.getAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(
            @PathVariable Long id) {

        orderService.delete(id);

        return ResponseEntity.ok(
                "Order Deleted Successfully");
    }


    @GetMapping("/buyer/{buyerId}")
    public ResponseEntity<List<OrderResponseDto>> getByBuyer(
            @PathVariable Long buyerId) {

        return ResponseEntity.ok(
                orderService.getByBuyer(buyerId));

    }

    @GetMapping("/buyer/{buyerId}/available")
    public ResponseEntity<List<OrderResponseDto>> getAvailableOrdersByBuyer(
            @PathVariable Long buyerId) {

        return ResponseEntity.ok(
                orderService.getAvailableOrdersByBuyer(buyerId));

    }

    @GetMapping("/available-for-raw-material-check")
    public ResponseEntity<List<OrderResponseDto>> getAvailableForRawMaterialCheck() {

        return ResponseEntity.ok(
                orderService.getAvailableForRawMaterialCheck());

    }

    @GetMapping("/available-for-fabric-check")
    public ResponseEntity<List<OrderResponseDto>> getAvailableForFabricCheck() {

        return ResponseEntity.ok(
                orderService.getAvailableForFabricCheck());

    }
}
