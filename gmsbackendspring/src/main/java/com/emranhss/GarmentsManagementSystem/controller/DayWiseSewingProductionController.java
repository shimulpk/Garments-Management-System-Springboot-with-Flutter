package com.emranhss.GarmentsManagementSystem.controller;

import com.emranhss.GarmentsManagementSystem.dto.request.DayWiseSewingProductionRequestDto;
import com.emranhss.GarmentsManagementSystem.dto.response.DayWiseSewingProductionResponseDto;
import com.emranhss.GarmentsManagementSystem.dto.response.LineWiseSewingProgressResponseDto;
import com.emranhss.GarmentsManagementSystem.dto.response.SewingPlanProgressResponseDto;
import com.emranhss.GarmentsManagementSystem.service.DayWiseSewingProductionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/day-wise-sewing-productions")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN','SEWING_MANAGER','PRODUCTION_MANAGER')")
public class DayWiseSewingProductionController {

    private final DayWiseSewingProductionService dayWiseSewingProductionService;

    @PostMapping
    public ResponseEntity<DayWiseSewingProductionResponseDto> create(
            @RequestBody DayWiseSewingProductionRequestDto request) {

        return ResponseEntity.ok(
                dayWiseSewingProductionService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DayWiseSewingProductionResponseDto> update(
            @PathVariable Long id,
            @RequestBody DayWiseSewingProductionRequestDto request) {

        return ResponseEntity.ok(
                dayWiseSewingProductionService.update(id, request));
    }

    @GetMapping("/sewing-plan/{sewingPlanId}")
    public ResponseEntity<List<DayWiseSewingProductionResponseDto>>
    getBySewingPlan(
            @PathVariable Long sewingPlanId) {

        return ResponseEntity.ok(
                dayWiseSewingProductionService
                        .getBySewingPlan(
                                sewingPlanId));
    }

    

    @GetMapping("/{id}")
    public ResponseEntity<DayWiseSewingProductionResponseDto> getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                dayWiseSewingProductionService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<DayWiseSewingProductionResponseDto>> getAll() {

        return ResponseEntity.ok(
                dayWiseSewingProductionService.getAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(
            @PathVariable Long id) {

        dayWiseSewingProductionService.delete(id);

        return ResponseEntity.ok(
                "Day Wise Sewing Production Deleted Successfully");
    }

//     Angular Progress Card Plan Input, Line Target,Achieved So Far,Remaining

    @GetMapping("/progress")
    public ResponseEntity<SewingPlanProgressResponseDto> getProgress(
            @RequestParam Long sewingPlanId,
            @RequestParam Long productionLineId) {

        return ResponseEntity.ok(
                dayWiseSewingProductionService.getProgress(
                        sewingPlanId,
                        productionLineId));
    }


    @GetMapping("/line-progress/{sewingPlanId}")
    public ResponseEntity<List<LineWiseSewingProgressResponseDto>>
    getLineWiseProgress(
            @PathVariable Long sewingPlanId){

        return ResponseEntity.ok(

                dayWiseSewingProductionService
                        .getLineWiseProgress(sewingPlanId));

    }
}
