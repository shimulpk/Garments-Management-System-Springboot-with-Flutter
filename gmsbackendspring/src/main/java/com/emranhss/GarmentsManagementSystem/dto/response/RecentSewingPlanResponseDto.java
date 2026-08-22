package com.emranhss.GarmentsManagementSystem.dto.response;

import com.emranhss.GarmentsManagementSystem.enums.SewingPlanStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class RecentSewingPlanResponseDto {

    private String planNo;

    private String styleNo;

    private String orderNo;

    private Integer outputQty;

    private SewingPlanStatus status;
}
