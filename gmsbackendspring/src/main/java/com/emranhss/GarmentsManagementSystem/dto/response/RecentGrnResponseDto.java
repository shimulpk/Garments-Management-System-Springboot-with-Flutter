package com.emranhss.GarmentsManagementSystem.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecentGrnResponseDto {

    private String grnNo;

    private LocalDate grnDate;

    private String supplier;

    private Double grandTotal;
}
