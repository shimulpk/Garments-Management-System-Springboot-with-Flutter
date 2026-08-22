package com.emranhss.GarmentsManagementSystem.dto.response;

import com.emranhss.GarmentsManagementSystem.enums.FinishingPlanStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecentFinishingPlanResponseDto {

    private String planNo;

    private String styleNo;

    private String buyerName;

    private Integer targetQty;

    private FinishingPlanStatus status;


}
