package com.emranhss.GarmentsManagementSystem.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashboardReportResponseDto {
    // Merchandising

    private Long totalBuyers;

    private Long totalStyles;

    private Long totalOrders;

    // Procurement

    private Long totalVendors;

    private Long totalPurchaseOrders;

    // Inventory

    private Long totalItems;

    private Long totalGrns;

    private Long totalMaterialIssues;

    // Production

    private Long totalProductionLines;

    private Long totalMachines;

    private Long totalPackingPlans;

    // Shipment

    private Long totalShipments;


    private Long totalCuttingProduction;

    private Long totalSewingProduction;

    private Long totalFinishingProduction;

    private Long totalPackingProduction;


}
