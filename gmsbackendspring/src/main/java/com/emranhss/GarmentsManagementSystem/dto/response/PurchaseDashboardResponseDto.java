package com.emranhss.GarmentsManagementSystem.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseDashboardResponseDto {


    // Summary

    private Long totalVendors;

    private Long pendingRequisitions;

    private Long pendingPurchaseOrders;

    private Long todayPurchaseOrders;

    private Long todayGoodsReceive;

    // Tables

    private List<RecentPurchaseOrderResponseDto> recentPurchaseOrders;

    private List<PendingRequisitionResponseDto> pendingRequisitionsList;

    private List<RecentGrnResponseDto> recentGoodsReceives;


}
