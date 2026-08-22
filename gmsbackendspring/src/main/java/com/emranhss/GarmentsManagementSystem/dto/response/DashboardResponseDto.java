package com.emranhss.GarmentsManagementSystem.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashboardResponseDto {

    private Long totalUsers;

    private Long totalBuyers;

    private Long totalVendors;

    private Long totalItems;

    private Long totalOrders;
    private Long totalShipments;

    private Long pendingRequisitions;

    private Long pendingPurchaseOrders;

    private Long currentStockItems;

    private Long todayCuttingPcs;

    private Long todaySewingPcs;

    private Long todayFinishingPcs;

    private Long todayPackingPcs;

    

    private List<OrderTrackingResponseDto> orderTracking;


}
