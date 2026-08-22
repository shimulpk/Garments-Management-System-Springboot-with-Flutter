package com.emranhss.GarmentsManagementSystem.controller;

import com.emranhss.GarmentsManagementSystem.dto.response.StockResponseDto;
import com.emranhss.GarmentsManagementSystem.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/stocks")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN','STORE_MANAGER')")
public class StockController {

    private final StockService stockService;

    // =====================================
    // Get All Stocks
    // =====================================

    @GetMapping
    public List<StockResponseDto> getAll() {

        return stockService.getAll();

    }

    // =====================================
    // Get Stock By Id
    // =====================================

    @GetMapping("/{id}")
    public StockResponseDto getById(
            @PathVariable Long id) {

        return stockService.getById(id);

    }

    @GetMapping("/available")
    public ResponseEntity<List<StockResponseDto>> getAvailableStocks() {

        return ResponseEntity.ok(
                stockService.getAvailableStocks()
        );

    }
}
