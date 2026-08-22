package com.emranhss.GarmentsManagementSystem.dto.mapper;

import com.emranhss.GarmentsManagementSystem.dto.response.PackingPlanResponseDto;
import com.emranhss.GarmentsManagementSystem.entity.PackingPlan;

public class PackingPlanMapper {

    public static PackingPlanResponseDto toDto(
            PackingPlan packingPlan) {

        if (packingPlan == null) {
            return null;
        }

        PackingPlanResponseDto dto =
                new PackingPlanResponseDto();

        dto.setId(
                packingPlan.getId());

        dto.setPackingPlanId(
                packingPlan.getPackingPlanId());

        dto.setFinishingPlanId(
                packingPlan.getFinishingPlan().getId());

        dto.setFinishingPlanCode(
                packingPlan.getFinishingPlan()
                        .getFinishingPlanId());

        dto.setOrderId(
                packingPlan.getOrder().getId());

        dto.setOrderNo(
                packingPlan.getOrderNo());


        dto.setBuyerName(
                packingPlan.getBuyerName());

        dto.setStyleNo(
                packingPlan.getStyleNo());

        dto.setColor(
                packingPlan.getColor());

        dto.setTotalOrderQty(
                packingPlan.getTotalOrderQty());

        dto.setInputQty(
                packingPlan.getInputQty());

        dto.setTotalPackedQty(
                packingPlan.getTotalPackedQty());

        dto.setRejectionQty(
                packingPlan.getRejectionQty());

        dto.setPackingMethod(
                packingPlan.getPackingMethod());

        dto.setPcsPerCarton(
                packingPlan.getPcsPerCarton());

        dto.setTotalPlannedCartons(
                packingPlan.getTotalPlannedCartons());

        dto.setPolyBagType(
                packingPlan.getPolyBagType());

        dto.setHangTag(
                packingPlan.getHangTag());

        dto.setPackingSupervisor(
                packingPlan.getPackingSupervisor());

        dto.setStartDate(
                packingPlan.getStartDate());

        dto.setExpectedShipmentDate(
                packingPlan.getExpectedShipmentDate());

        dto.setStatus(
                packingPlan.getStatus());

        dto.setDestination(
                packingPlan.getOrder().getShippingAddress()
        );


        return dto;
    }
}
