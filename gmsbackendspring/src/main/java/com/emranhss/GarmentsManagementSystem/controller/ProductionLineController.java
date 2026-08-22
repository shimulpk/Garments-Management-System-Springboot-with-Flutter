package com.emranhss.GarmentsManagementSystem.controller;

import com.emranhss.GarmentsManagementSystem.dto.request.ProductionLineRequestDto;
import com.emranhss.GarmentsManagementSystem.dto.response.ProductionLineResponseDto;
import com.emranhss.GarmentsManagementSystem.service.ProductionLineService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/production-lines")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN','PRODUCTION_MANAGER','SEWING_MANAGER')")
public class ProductionLineController {

    private final ProductionLineService productionLineService;

    @PostMapping
    public ResponseEntity<ProductionLineResponseDto> create(
            @RequestBody ProductionLineRequestDto request) {
        return ResponseEntity.ok(productionLineService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductionLineResponseDto> update(
            @PathVariable Long id,
            @RequestBody ProductionLineRequestDto request) {
        return ResponseEntity.ok(productionLineService.update(id, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductionLineResponseDto> getById(
            @PathVariable Long id) {
        return ResponseEntity.ok(productionLineService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<ProductionLineResponseDto>> getAll() {
        return ResponseEntity.ok(productionLineService.getAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(
            @PathVariable Long id) {
        productionLineService.delete(id);
        return ResponseEntity.ok("Production Line Deleted Successfully");
    }
}
