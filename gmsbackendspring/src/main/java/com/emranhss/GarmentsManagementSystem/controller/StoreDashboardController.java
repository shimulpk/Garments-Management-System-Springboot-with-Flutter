package com.emranhss.GarmentsManagementSystem.controller;

import com.emranhss.GarmentsManagementSystem.dto.response.StoreDashboardResponseDto;
import com.emranhss.GarmentsManagementSystem.service.StoreDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard/store")
@RequiredArgsConstructor
@PreAuthorize("hasRole('STORE_MANAGER')")
public class StoreDashboardController {
    private final StoreDashboardService storeDashboardService;

    @GetMapping
    public ResponseEntity<StoreDashboardResponseDto> getDashboard() {

        return ResponseEntity.ok(
                storeDashboardService.getDashboard()
        );

    }
}
