package com.emranhss.GarmentsManagementSystem.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShipmentRequestDto {

    private Long packingPlanId;

    private LocalDate shipmentDate;

    private String remarks;
}
