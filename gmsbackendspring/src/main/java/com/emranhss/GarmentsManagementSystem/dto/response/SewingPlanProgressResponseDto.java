package com.emranhss.GarmentsManagementSystem.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class SewingPlanProgressResponseDto {
//    Sewing Plan Input Qty
//      Example: 6000 pcs

    private Integer planInput;


//      Selected Line Target
//     Example: L-1 = 2000 pcs

    private Integer lineTarget;

//
//      Total Achieved by this Line
//      Example: 2000 pcs

    private Integer achievedSoFar;


//     Remaining for this Line
//      lineTarget - achievedSoFar

    private Integer remaining;
}
