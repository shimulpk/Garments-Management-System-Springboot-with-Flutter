package com.emranhss.GarmentsManagementSystem.dto.response;

import com.emranhss.GarmentsManagementSystem.enums.FinishingPlanStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class FinishingPlanResponseDto {

    private Long id;

    private String finishingPlanId;

    private Long sewingPlanId;

    private String sewingPlanCode;

    private String buyerName;

    private String orderNo;

    private String styleNo;

    private String color;

    private Integer inputQty;

    private Integer targetQty;

    private Integer passQty;

    private Integer rejectionQty;

    private Boolean procTrimming;

    private Boolean procIroning;

    private Boolean procWashing;

    private Boolean procButtonAttach;

    private String finishingTableNo;

    private String supervisorName;

    private LocalDate startDate;

    private LocalDate endDate;

    private FinishingPlanStatus status;
}
