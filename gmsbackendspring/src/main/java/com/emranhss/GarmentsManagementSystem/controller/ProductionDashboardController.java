package com.emranhss.GarmentsManagementSystem.controller;

import com.emranhss.GarmentsManagementSystem.dto.response.ProductionDashboardResponseDto;
import com.emranhss.GarmentsManagementSystem.service.ProductionDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard/production")
@RequiredArgsConstructor
@PreAuthorize("hasRole('PRODUCTION_MANAGER')")
public class ProductionDashboardController {

    private final ProductionDashboardService productionDashboardService;


    @GetMapping
    public ResponseEntity<ProductionDashboardResponseDto> getDashboard() {

        return ResponseEntity.ok(
                productionDashboardService.getDashboard()
        );

    }

}
