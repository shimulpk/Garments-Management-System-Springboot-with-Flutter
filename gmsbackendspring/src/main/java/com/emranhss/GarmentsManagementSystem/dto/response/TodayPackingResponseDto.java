package com.emranhss.GarmentsManagementSystem.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TodayPackingResponseDto {

    private String styleNo;

    private String buyerName;

    private String orderNo;

    private Long packedQty;

    private Long rejectQty;

}
