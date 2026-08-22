package com.emranhss.GarmentsManagementSystem.controller;

import com.emranhss.GarmentsManagementSystem.dto.request.BomViewRequestDto;
import com.emranhss.GarmentsManagementSystem.dto.response.BomViewResponseDto;
import com.emranhss.GarmentsManagementSystem.service.BomViewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bom-views")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN','MERCHANDISER')")
public class BomViewController {
    private final BomViewService bomViewService;

    @PostMapping
    public ResponseEntity<BomViewResponseDto> create(
            @RequestBody BomViewRequestDto request) {

        return ResponseEntity.ok(
                bomViewService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BomViewResponseDto> update(
            @PathVariable Long id,
            @RequestBody BomViewRequestDto request) {

        return ResponseEntity.ok(
                bomViewService.update(id, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BomViewResponseDto> getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                bomViewService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<BomViewResponseDto>> getAll() {

        return ResponseEntity.ok(
                bomViewService.getAll());
    }

    @GetMapping("/style/{styleId}")
    public ResponseEntity<List<BomViewResponseDto>> getByStyle(
            @PathVariable Long styleId) {

        return ResponseEntity.ok(
                bomViewService.getByStyle(styleId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(
            @PathVariable Long id) {

        bomViewService.delete(id);

        return ResponseEntity.ok(
                "BOM View Deleted Successfully");
    }
}
