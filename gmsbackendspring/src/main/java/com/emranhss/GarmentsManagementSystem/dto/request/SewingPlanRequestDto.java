package com.emranhss.GarmentsManagementSystem.dto.request;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class SewingPlanRequestDto {
    private Long cuttingPlanId;

    private LocalDate startDate;

    private LocalDate endDate;

    private List<SewingTargetRequestDto> targets;
}
