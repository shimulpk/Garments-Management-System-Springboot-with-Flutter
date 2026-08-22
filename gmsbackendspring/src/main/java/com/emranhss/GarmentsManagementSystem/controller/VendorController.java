package com.emranhss.GarmentsManagementSystem.controller;

import com.emranhss.GarmentsManagementSystem.dto.request.VendorRequestDto;
import com.emranhss.GarmentsManagementSystem.dto.response.VendorResponseDto;
import com.emranhss.GarmentsManagementSystem.service.VendorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vendors")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN','PURCHASE_MANAGER')")
public class VendorController {
    private final VendorService vendorService;

    @PostMapping
    public ResponseEntity<VendorResponseDto> create(
            @RequestBody VendorRequestDto request) {

        return ResponseEntity.ok(
                vendorService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VendorResponseDto> update(
            @PathVariable Long id,
            @RequestBody VendorRequestDto request) {

        return ResponseEntity.ok(
                vendorService.update(id, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<VendorResponseDto> getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                vendorService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<VendorResponseDto>> getAll() {

        return ResponseEntity.ok(
                vendorService.getAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(
            @PathVariable Long id) {

        vendorService.delete(id);

        return ResponseEntity.ok(
                "Vendor Deleted Successfully");
    }
}
