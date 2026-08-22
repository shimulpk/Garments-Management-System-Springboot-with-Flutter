package com.emranhss.GarmentsManagementSystem.controller;

import com.emranhss.GarmentsManagementSystem.dto.request.UomRequestDto;
import com.emranhss.GarmentsManagementSystem.dto.response.UomResponseDto;
import com.emranhss.GarmentsManagementSystem.service.UomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/uoms")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN','MERCHANDISER')")
public class UomController {

    private final UomService uomService;

    @PostMapping
    public ResponseEntity<UomResponseDto> create(
            @RequestBody UomRequestDto request) {

        return ResponseEntity.ok(
                uomService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UomResponseDto> update(
            @PathVariable Long id,
            @RequestBody UomRequestDto request) {

        return ResponseEntity.ok(
                uomService.update(id, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UomResponseDto> getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                uomService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<UomResponseDto>> getAll() {

        return ResponseEntity.ok(
                uomService.getAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(
            @PathVariable Long id) {

        uomService.delete(id);

        return ResponseEntity.ok(
                "UOM Deleted Successfully");
    }
}
