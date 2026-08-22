package com.emranhss.GarmentsManagementSystem.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MerchandiserDashboardResponseDto {

    // Summary Cards

    private Long totalBuyers;

    private Long totalStyles;

    private Long totalOrders;

    private Long totalBom;

    private Long totalRawMaterialChecks;

    private Long totalFabricChecks;

    // Recent Orders

    private List<MerchandiserOrderResponseDto> recentOrders;
}
