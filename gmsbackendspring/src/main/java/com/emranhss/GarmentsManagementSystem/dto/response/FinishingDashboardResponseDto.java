package com.emranhss.GarmentsManagementSystem.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FinishingDashboardResponseDto {


    // Summary

    private Long todayFinished;

    private Long todayReject;

    private Long totalPlans;

    private Long pendingPlans;

    private Long completedPlans;

    // Tables

    private List<TodayFinishingResponseDto> todayFinishings;

    private List<RecentFinishingPlanResponseDto> recentPlans;
}
