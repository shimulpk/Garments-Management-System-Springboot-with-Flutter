package com.emranhss.GarmentsManagementSystem.controller;

import com.emranhss.GarmentsManagementSystem.dto.response.PackingDashboardResponseDto;
import com.emranhss.GarmentsManagementSystem.service.PackingDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard/packing")
@RequiredArgsConstructor
@PreAuthorize("hasRole('PACKING_MANAGER')")
public class PackingDashboardController {

    private final PackingDashboardService packingDashboardService;

    @GetMapping
    public ResponseEntity<PackingDashboardResponseDto> getDashboard() {

        return ResponseEntity.ok(
                packingDashboardService.getDashboard()
        );

    }

}
