package com.emranhss.GarmentsManagementSystem.dto.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class FinishingPlanRequestDto {

    private Long sewingPlanId;

    private Boolean procTrimming;

    private Boolean procIroning;

    private Boolean procWashing;

    private Boolean procButtonAttach;

    private String finishingTableNo;

    private String supervisorName;

    private LocalDate startDate;

    private LocalDate endDate;
}
