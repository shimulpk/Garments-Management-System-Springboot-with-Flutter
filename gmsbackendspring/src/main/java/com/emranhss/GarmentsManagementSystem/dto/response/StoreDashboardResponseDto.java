package com.emranhss.GarmentsManagementSystem.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoreDashboardResponseDto {
    // Summary

    private Long totalItems;

    private Long currentStockItems;

    private Long lowStockItems;

    private Long pendingStoreRequisitions;

    private Long todayGoodsReceive;

    private Long todayMaterialIssue;

    // Tables

    private List<RecentGrnResponseDto> recentGoodsReceives;

    private List<RecentMaterialIssueResponseDto> recentMaterialIssues;

    private List<LowStockResponseDto> lowStockList;

}
