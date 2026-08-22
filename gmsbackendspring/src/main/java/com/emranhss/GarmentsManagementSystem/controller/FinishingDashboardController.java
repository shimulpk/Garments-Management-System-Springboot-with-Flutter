package com.emranhss.GarmentsManagementSystem.controller;

import com.emranhss.GarmentsManagementSystem.dto.response.FinishingDashboardResponseDto;
import com.emranhss.GarmentsManagementSystem.service.FinishingDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard/finishing")
@RequiredArgsConstructor
@PreAuthorize("hasRole('FINISHING_MANAGER')")
public class FinishingDashboardController {

    private final FinishingDashboardService finishingDashboardService;


    @GetMapping
    public ResponseEntity<FinishingDashboardResponseDto> getDashboard() {

        return ResponseEntity.ok(
                finishingDashboardService.getDashboard()
        );

    }
}
