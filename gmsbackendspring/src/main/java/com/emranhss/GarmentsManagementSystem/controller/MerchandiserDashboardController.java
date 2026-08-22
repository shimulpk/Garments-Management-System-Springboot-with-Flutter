package com.emranhss.GarmentsManagementSystem.controller;

import com.emranhss.GarmentsManagementSystem.dto.response.MerchandiserDashboardResponseDto;
import com.emranhss.GarmentsManagementSystem.service.MerchandiserDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard/merchandiser")
@RequiredArgsConstructor
@PreAuthorize("hasRole('MERCHANDISER')")
public class MerchandiserDashboardController {
    private final MerchandiserDashboardService merchandiserDashboardService;

    @GetMapping
    public ResponseEntity<MerchandiserDashboardResponseDto> getDashboard() {

        return ResponseEntity.ok(
                merchandiserDashboardService.getDashboard()
        );

    }
}
