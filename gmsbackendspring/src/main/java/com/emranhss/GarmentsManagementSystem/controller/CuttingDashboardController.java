package com.emranhss.GarmentsManagementSystem.controller;

import com.emranhss.GarmentsManagementSystem.dto.response.CuttingDashboardResponseDto;
import com.emranhss.GarmentsManagementSystem.service.CuttingDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard/cutting")
@RequiredArgsConstructor
@PreAuthorize("hasRole('CUTTING_MANAGER')")
public class CuttingDashboardController {

    private final CuttingDashboardService cuttingDashboardService;

    @GetMapping
    public ResponseEntity<CuttingDashboardResponseDto> getDashboard() {

        return ResponseEntity.ok(
                cuttingDashboardService.getDashboard()
        );

    }
}
