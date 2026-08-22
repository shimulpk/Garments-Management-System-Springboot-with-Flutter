package com.emranhss.GarmentsManagementSystem.dto.response;

import com.emranhss.GarmentsManagementSystem.enums.PackingPlanStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecentPackingPlanResponseDto {

    private String planNo;

    private String styleNo;

    private String buyerName;

    private Integer inputQty;

    private PackingPlanStatus status;
}
