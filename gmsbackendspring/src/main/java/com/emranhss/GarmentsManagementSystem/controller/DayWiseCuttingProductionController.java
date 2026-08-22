package com.emranhss.GarmentsManagementSystem.controller;

import com.emranhss.GarmentsManagementSystem.dto.request.DayWiseCuttingProductionRequestDto;
import com.emranhss.GarmentsManagementSystem.dto.request.DayWiseCuttingProductionUpdateRequestDto;
import com.emranhss.GarmentsManagementSystem.dto.response.*;
import com.emranhss.GarmentsManagementSystem.service.DayWiseCuttingProductionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/day-wise-cutting-production")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN','CUTTING_MANAGER','PRODUCTION_MANAGER')")
public class DayWiseCuttingProductionController {

    private final DayWiseCuttingProductionService service;

    @PostMapping
    public ResponseEntity<DayWiseCuttingProductionResponseDto>  create(
            @RequestBody DayWiseCuttingProductionRequestDto request) {

       return ResponseEntity.ok(service.create(request));
    }

// for android use
    @PutMapping("/{id}")
    public ResponseEntity<DayWiseCuttingProductionResponseDto> update(

            @PathVariable Long id,

            @RequestBody DayWiseCuttingProductionUpdateRequestDto requestDto) {

        return ResponseEntity.ok(

                service.update(id, requestDto)

        );

    }


//    Android use this api for history
@GetMapping("/history")
public ResponseEntity<List<DayWiseCuttingHistoryResponseDto>> getHistory(
        @RequestParam(required = false) LocalDate date) {

    if (date == null) {
        return ResponseEntity.ok(
                service.getHistory()
        );
    }

    return ResponseEntity.ok(
            service.getHistory(date)
    );
}

    //    Android use this api for history details
    @GetMapping("/history/details")
    public ResponseEntity<List<DayWiseCuttingHistoryDetailsResponseDto>> getHistoryDetails(

            @RequestParam Long cuttingPlanId,

            @RequestParam LocalDate date) {

        return ResponseEntity.ok(

                service.getHistoryDetails(
                        cuttingPlanId,
                        date
                )

        );

    }

    @GetMapping("/{id}")
    public ResponseEntity<DayWiseCuttingProductionResponseDto>  getById(
            @PathVariable Long id) {

       return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<DayWiseCuttingProductionResponseDto>>  getAll() {

        return ResponseEntity.ok(service.getAll());
    }

    @DeleteMapping("/{id}")
    public void delete(
            @PathVariable Long id) {

        service.delete(id);
    }

    @GetMapping("/progress/{cuttingPlanId}")
    public ResponseEntity<CuttingPlanProgressResponseDto>  getProgress(
            @PathVariable Long cuttingPlanId) {

        return ResponseEntity.ok(service.getProgress(cuttingPlanId));
    }

    @GetMapping("/cutting-plan/{cuttingPlanId}")
    public ResponseEntity<List<DayWiseCuttingProductionResponseDto>>
    getByCuttingPlan(
            @PathVariable Long cuttingPlanId) {

        return ResponseEntity.ok(
                service.getByCuttingPlan(cuttingPlanId));

    }

//For Android use
    @GetMapping("/history-details-summary")
    public ResponseEntity<DayWiseCuttingHistorySummaryResponseDto> getHistorySummary(

            @RequestParam Long cuttingPlanId,

            @RequestParam LocalDate date) {

        return ResponseEntity.ok(

                service.getHistorySummary(
                        cuttingPlanId,
                        date
                )

        );

    }
}
