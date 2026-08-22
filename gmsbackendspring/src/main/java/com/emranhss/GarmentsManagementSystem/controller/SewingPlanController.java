package com.emranhss.GarmentsManagementSystem.controller;

import com.emranhss.GarmentsManagementSystem.dto.request.SewingPlanRequestDto;
import com.emranhss.GarmentsManagementSystem.dto.response.SewingPlanResponseDto;
import com.emranhss.GarmentsManagementSystem.service.SewingPlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sewing-plans")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN','SEWING_MANAGER','PRODUCTION_MANAGER','FINISHING_MANAGER')")
public class SewingPlanController {
    private final SewingPlanService sewingPlanService;

    @PostMapping
    public ResponseEntity<SewingPlanResponseDto> create(
            @RequestBody SewingPlanRequestDto request) {

        return ResponseEntity.ok(
                sewingPlanService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SewingPlanResponseDto> update(
            @PathVariable Long id,
            @RequestBody SewingPlanRequestDto request) {

        return ResponseEntity.ok(
                sewingPlanService.update(id, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SewingPlanResponseDto> getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                sewingPlanService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<SewingPlanResponseDto>> getAll() {

        return ResponseEntity.ok(
                sewingPlanService.getAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(
            @PathVariable Long id) {

        sewingPlanService.delete(id);

        return ResponseEntity.ok(
                "Sewing Plan Deleted Successfully");
    }


    @GetMapping("/available-for-day-wise-production")
    public ResponseEntity<List<SewingPlanResponseDto>> getAvailableForDayWiseProduction() {

        return ResponseEntity.ok(
                sewingPlanService.getAvailableForDayWiseProduction()
        );
    }

}

