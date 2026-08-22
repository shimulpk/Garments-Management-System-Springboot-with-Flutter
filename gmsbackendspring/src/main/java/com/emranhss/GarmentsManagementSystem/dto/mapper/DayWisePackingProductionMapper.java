package com.emranhss.GarmentsManagementSystem.dto.mapper;

import com.emranhss.GarmentsManagementSystem.dto.response.DayWisePackingProductionResponseDto;
import com.emranhss.GarmentsManagementSystem.entity.DayWisePackingProduction;

public class DayWisePackingProductionMapper {

    public static DayWisePackingProductionResponseDto toDto(
            DayWisePackingProduction production) {

        if (production == null) {
            return null;
        }

        DayWisePackingProductionResponseDto dto =
                new DayWisePackingProductionResponseDto();

        dto.setId(
                production.getId());

        dto.setPackingPlanId(
                production.getPackingPlan().getId());

        dto.setPackingPlanCode(
                production.getPackingPlan()
                        .getPackingPlanId());

        dto.setBuyerName(
                production.getBuyerName());

        dto.setOrderNo(
                production.getOrderNo());

        dto.setStyleNo(
                production.getStyleNo());

        dto.setDate(
                production.getDate());

        dto.setTodayPackedQty(
                production.getTodayPackedQty());

        dto.setTodayPackedCartons(
                production.getTodayPackedCartons());

        dto.setTodayRejectQty(
                production.getTodayRejectQty());

        dto.setCreatedAt(production.getCreatedAt());

        return dto;
    }

}
