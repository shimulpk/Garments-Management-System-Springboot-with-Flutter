package com.emranhss.GarmentsManagementSystem.controller;

import com.emranhss.GarmentsManagementSystem.dto.response.PurchaseDashboardResponseDto;
import com.emranhss.GarmentsManagementSystem.service.PurchaseDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard/purchase")
@RequiredArgsConstructor
@PreAuthorize("hasRole('PURCHASE_MANAGER')")
public class PurchaseDashboardController {

    private final PurchaseDashboardService purchaseDashboardService;

    @GetMapping
    public ResponseEntity<PurchaseDashboardResponseDto> getDashboard() {

        return ResponseEntity.ok(
                purchaseDashboardService.getDashboard()
        );

    }

}
