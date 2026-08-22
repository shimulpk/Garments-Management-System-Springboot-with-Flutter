package com.emranhss.GarmentsManagementSystem.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecentPurchaseOrderResponseDto {

    private String poNo;

    private LocalDate poDate;

    private String vendorName;

    private Double grandTotal;

    private String status;
}
