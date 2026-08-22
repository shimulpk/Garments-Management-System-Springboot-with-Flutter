package com.emranhss.GarmentsManagementSystem.controller;

import com.emranhss.GarmentsManagementSystem.dto.response.ShipmentDashboardResponseDto;
import com.emranhss.GarmentsManagementSystem.service.ShipmentDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard/shipment")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN','SHIPMENT_EXECUTIVE','PRODUCTION_MANAGER')")
public class ShipmentDashboardController {

    private final ShipmentDashboardService shipmentDashboardService;

    @GetMapping
    public ResponseEntity<ShipmentDashboardResponseDto> getDashboard() {

        return ResponseEntity.ok(
                shipmentDashboardService.getDashboard()
        );

    }

}
