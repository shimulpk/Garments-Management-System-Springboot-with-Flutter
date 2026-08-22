package com.emranhss.GarmentsManagementSystem.dto.response;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class DayWisePackingProductionResponseDto {

    private Long id;

    private Long packingPlanId;

    private String packingPlanCode;

    private String buyerName;

    private String orderNo;

    private String styleNo;

    private LocalDate date;

    private Integer todayPackedQty;

    private Integer todayPackedCartons;

    private Integer todayRejectQty;

    private LocalDateTime createdAt;
}
