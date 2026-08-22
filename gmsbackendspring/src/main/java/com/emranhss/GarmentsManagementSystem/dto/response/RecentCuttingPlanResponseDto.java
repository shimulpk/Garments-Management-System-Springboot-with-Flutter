package com.emranhss.GarmentsManagementSystem.dto.response;

import com.emranhss.GarmentsManagementSystem.enums.CuttingPlanStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecentCuttingPlanResponseDto {


    private String planNo;

    private String styleNo;

    private String orderNo;

    private Integer targetQty;

    private CuttingPlanStatus status;

}
