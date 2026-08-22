package com.emranhss.GarmentsManagementSystem.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SewingDashboardResponseDto {

    // Summary

    private Long todaySewing;

    private Long todayReject;

    private Long totalPlans;

    private Long pendingPlans;

    private Long completedPlans;

    // Tables

    private List<TodaySewingResponseDto> todaySewings;

    private List<RecentSewingPlanResponseDto> recentPlans;
}
