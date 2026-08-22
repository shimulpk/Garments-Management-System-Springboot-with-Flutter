package com.emranhss.GarmentsManagementSystem.dto.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class DayWisePackingProductionRequestDto {

    private Long packingPlanId;

    private LocalDate date;

    private Integer todayPackedQty;

    private Integer todayRejectQty;


}
