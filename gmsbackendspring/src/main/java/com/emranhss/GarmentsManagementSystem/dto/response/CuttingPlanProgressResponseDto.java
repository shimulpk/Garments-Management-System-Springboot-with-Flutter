package com.emranhss.GarmentsManagementSystem.dto.response;

import com.emranhss.GarmentsManagementSystem.enums.CuttingPlanStatus;
import lombok.Data;

@Data
public class CuttingPlanProgressResponseDto {
    private Integer target;

    private Integer cutSoFar;

    private Integer remaining;

    private Double progress;
    private Integer rejected;   

    private CuttingPlanStatus status;
}
