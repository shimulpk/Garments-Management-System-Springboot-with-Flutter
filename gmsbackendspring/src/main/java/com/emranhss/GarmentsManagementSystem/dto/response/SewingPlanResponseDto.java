package com.emranhss.GarmentsManagementSystem.dto.response;

import com.emranhss.GarmentsManagementSystem.enums.SewingPlanStatus;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class SewingPlanResponseDto {
    private Long id;

    private String sewingPlanId;

    private Long cuttingPlanId;

    private String cuttingPlanCode;

    private String buyerName;

    private String orderNo;

    private String styleNo;

    private String color;

    private Integer inputReceivedQty;

    private Integer outputQty;

    private Integer rejectionQty;

    private LocalDate startDate;

    private LocalDate endDate;

    private SewingPlanStatus status;

    private List<SewingTargetResponseDto> targets;

    private List<SewingLineProgressResponseDto> lineProgress;
}
