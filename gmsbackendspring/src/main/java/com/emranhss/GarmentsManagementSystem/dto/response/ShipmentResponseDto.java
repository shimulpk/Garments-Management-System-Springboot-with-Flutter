package com.emranhss.GarmentsManagementSystem.dto.response;

import com.emranhss.GarmentsManagementSystem.enums.ShipmentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShipmentResponseDto {

    private Long id;

    private String shipmentNo;

    private Long packingPlanId;

    private String packingPlanNo;

    private String buyerName;

    private String destination;

    private String orderNo;

    private String styleNo;


    private Integer shipmentQty;

    private LocalDate shipmentDate;

    private String remarks;

    private ShipmentStatus status;

}
