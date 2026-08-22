package com.emranhss.GarmentsManagementSystem.dto.mapper;

import com.emranhss.GarmentsManagementSystem.dto.response.ShipmentResponseDto;
import com.emranhss.GarmentsManagementSystem.entity.Shipment;

public class ShipmentMapper {

    private ShipmentMapper() {
    }

    public static ShipmentResponseDto toDto(Shipment shipment) {

        ShipmentResponseDto dto = new ShipmentResponseDto();

        dto.setId(
                shipment.getId());

        dto.setShipmentNo(
                shipment.getShipmentNo());

        dto.setPackingPlanId(
                shipment.getPackingPlan().getId());

        dto.setPackingPlanNo(
                shipment.getPackingPlan().getPackingPlanId());

        dto.setBuyerName(
                shipment.getBuyerName());

        dto.setDestination(
                shipment.getDestination());

        dto.setOrderNo(
                shipment.getOrderNo());

        dto.setStyleNo(
                shipment.getStyleNo());

        dto.setShipmentQty(
                shipment.getShipmentQty());

        dto.setShipmentDate(
                shipment.getShipmentDate());

        dto.setRemarks(
                shipment.getRemarks());

        dto.setStatus(
                shipment.getStatus());

        return dto;

    }

}
