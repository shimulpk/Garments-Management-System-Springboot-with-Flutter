package com.emranhss.GarmentsManagementSystem.controller;

import com.emranhss.GarmentsManagementSystem.dto.request.BomStyleRequestDto;
import com.emranhss.GarmentsManagementSystem.dto.response.BomStyleResponseDto;
import com.emranhss.GarmentsManagementSystem.service.BomStyleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/styles")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN','MERCHANDISER')")
public class BomStyleController {
    private final BomStyleService bomStyleService;

    @PostMapping
    public ResponseEntity<BomStyleResponseDto> create(
            @RequestBody BomStyleRequestDto request) {

        return ResponseEntity.ok(
                bomStyleService.create(request)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<BomStyleResponseDto> update(
            @PathVariable Long id,
            @RequestBody BomStyleRequestDto request) {

        return ResponseEntity.ok(
                bomStyleService.update(id, request)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<BomStyleResponseDto> getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                bomStyleService.getById(id)
        );
    }

    @GetMapping
    public ResponseEntity<List<BomStyleResponseDto>> getAll() {

        return ResponseEntity.ok(
                bomStyleService.getAll()
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(
            @PathVariable Long id) {

        bomStyleService.delete(id);

        return ResponseEntity.ok(
                "Style Deleted Successfully"
        );
    }

    @GetMapping("/buyer/{buyerId}")
    public ResponseEntity<List<BomStyleResponseDto>> getByBuyer(
            @PathVariable Long buyerId) {

        return ResponseEntity.ok(
                bomStyleService.getByBuyer(buyerId)
        );

    }
}
