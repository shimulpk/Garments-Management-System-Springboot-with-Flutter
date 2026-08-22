package com.emranhss.GarmentsManagementSystem.controller;

import com.emranhss.GarmentsManagementSystem.dto.request.FinishingPlanRequestDto;
import com.emranhss.GarmentsManagementSystem.dto.response.FinishingPlanResponseDto;
import com.emranhss.GarmentsManagementSystem.dto.response.SewingPlanResponseDto;
import com.emranhss.GarmentsManagementSystem.service.FinishingPlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/finishing-plans")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN','FINISHING_MANAGER','PRODUCTION_MANAGER','PACKING_MANAGER')")
public class FinishingPlanController {

    private final FinishingPlanService finishingPlanService;

    @PostMapping
    public ResponseEntity<FinishingPlanResponseDto> create(
            @RequestBody FinishingPlanRequestDto request) {

        return ResponseEntity.ok(
                finishingPlanService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FinishingPlanResponseDto> update(
            @PathVariable Long id,
            @RequestBody FinishingPlanRequestDto request) {

        return ResponseEntity.ok(
                finishingPlanService.update(id, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FinishingPlanResponseDto> getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                finishingPlanService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<FinishingPlanResponseDto>> getAll() {

        return ResponseEntity.ok(
                finishingPlanService.getAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(
            @PathVariable Long id) {

        finishingPlanService.delete(id);

        return ResponseEntity.ok(
                "Finishing Plan Deleted Successfully");
    }

    @GetMapping("/available-sewing-plans")
    public ResponseEntity<List<SewingPlanResponseDto>> getAvailableSewingPlans() {

        return ResponseEntity.ok(
                finishingPlanService.getAvailableSewingPlans()
        );

    }
}
