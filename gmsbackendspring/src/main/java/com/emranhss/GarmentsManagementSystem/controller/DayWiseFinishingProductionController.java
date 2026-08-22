package com.emranhss.GarmentsManagementSystem.controller;

import com.emranhss.GarmentsManagementSystem.dto.request.DayWiseFinishingProductionRequestDto;
import com.emranhss.GarmentsManagementSystem.dto.response.DayWiseFinishingProductionResponseDto;
import com.emranhss.GarmentsManagementSystem.dto.response.FinishingProgressResponseDto;
import com.emranhss.GarmentsManagementSystem.service.DayWiseFinishingProductionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/day-wise-finishing-productions")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN','FINISHING_MANAGER','PRODUCTION_MANAGER')")
public class DayWiseFinishingProductionController {

    private final DayWiseFinishingProductionService dayWiseFinishingProductionService;

    @PostMapping
    public ResponseEntity<DayWiseFinishingProductionResponseDto> create(
            @RequestBody DayWiseFinishingProductionRequestDto request) {

        return ResponseEntity.ok(
                dayWiseFinishingProductionService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DayWiseFinishingProductionResponseDto> update(
            @PathVariable Long id,
            @RequestBody DayWiseFinishingProductionRequestDto request) {

        return ResponseEntity.ok(
                dayWiseFinishingProductionService.update(id, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DayWiseFinishingProductionResponseDto> getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                dayWiseFinishingProductionService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<DayWiseFinishingProductionResponseDto>> getAll() {

        return ResponseEntity.ok(
                dayWiseFinishingProductionService.getAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(
            @PathVariable Long id) {

        dayWiseFinishingProductionService.delete(id);

        return ResponseEntity.ok(
                "Day Wise Finishing Production Deleted Successfully");
    }

//    Target Quantity  Total Pass So Far, Remaining, Remaining

    @GetMapping("/progress")
    public ResponseEntity<FinishingProgressResponseDto> getProgress(
            @RequestParam Long finishingPlanId) {

        return ResponseEntity.ok(
                dayWiseFinishingProductionService.getProgress(
                        finishingPlanId));
    }


    @GetMapping("/finishing-plan/{finishingPlanId}")
    public ResponseEntity<List<DayWiseFinishingProductionResponseDto>>
    getByFinishingPlan(
            @PathVariable Long finishingPlanId) {

        return ResponseEntity.ok(

                dayWiseFinishingProductionService
                        .getByFinishingPlan(
                                finishingPlanId)

        );

    }

}
