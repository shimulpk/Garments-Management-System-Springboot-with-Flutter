package com.emranhss.GarmentsManagementSystem.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FinishingProgressResponseDto {

//     Target Quantity From Finishing Plan

    private Integer targetQuantity;

//  Total Pass Quantity So Far

    private Integer totalPassSoFar;

//     Remaining Quantity

    private Integer remaining;
}
