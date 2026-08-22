package com.emranhss.GarmentsManagementSystem.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShipmentDashboardResponseDto {

    // Summary

    private Long todayShipments;

    private Long totalShipments;

    private Long pendingShipments;

    private Long shippedShipments;

    // Table

    private List<RecentShipmentResponseDto> recentShipments;
}
