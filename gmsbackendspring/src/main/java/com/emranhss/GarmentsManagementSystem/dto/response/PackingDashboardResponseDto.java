package com.emranhss.GarmentsManagementSystem.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PackingDashboardResponseDto {

    // Summary

    private Long todayPacked;

    private Long todayReject;

    private Long totalPlans;

    private Long pendingPlans;

    private Long completedPlans;

    // Tables

    private List<TodayPackingResponseDto> todayPackings;

    private List<RecentPackingPlanResponseDto> recentPlans;
}
