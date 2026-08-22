package com.emranhss.GarmentsManagementSystem.dto.response;

import com.emranhss.GarmentsManagementSystem.enums.ShipmentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecentShipmentResponseDto {

    private String shipmentNo;

    private String styleNo;

    private String buyerName;

    private String destination;

    private Integer shipmentQty;

    private LocalDate shipmentDate;

    private ShipmentStatus status;
}
