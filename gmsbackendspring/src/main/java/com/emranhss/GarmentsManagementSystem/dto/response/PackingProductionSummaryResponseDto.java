package com.emranhss.GarmentsManagementSystem.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PackingProductionSummaryResponseDto {

    private Long packingPlanId;

    private String packingPlanCode;

    private String buyerName;

    private String orderNo;

    private String styleNo;

    private String color;

    private Integer targetQty;

    private Integer packedSoFar;

    private Integer remaining;

    private String status;
}
