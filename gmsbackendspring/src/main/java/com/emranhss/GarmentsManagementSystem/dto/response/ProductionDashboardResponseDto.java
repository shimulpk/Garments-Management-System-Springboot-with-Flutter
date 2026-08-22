package com.emranhss.GarmentsManagementSystem.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductionDashboardResponseDto {

    // Summary Cards

    // Summary Cards

    private Long pendingOrders;

    private Long confirmedOrders;

    private Long todayCutting;

    private Long todaySewing;

    private Long todayFinishing;

    private Long todayPacking;

    // Today Production

    private List<TodayProductionResponseDto> todayProductions;
}
