package com.emranhss.GarmentsManagementSystem.dto.mapper;

import com.emranhss.GarmentsManagementSystem.dto.response.DayWiseFinishingProductionResponseDto;
import com.emranhss.GarmentsManagementSystem.entity.DayWiseFinishingProduction;

public class DayWiseFinishingProductionMapper {

    public static DayWiseFinishingProductionResponseDto toDto(
            DayWiseFinishingProduction production) {

        if (production == null) {
            return null;
        }

        DayWiseFinishingProductionResponseDto dto =
                new DayWiseFinishingProductionResponseDto();

        dto.setId(
                production.getId());

        dto.setFinishingPlanId(
                production.getFinishingPlan().getId());

        dto.setFinishingPlanCode(
                production.getFinishingPlan().getFinishingPlanId());

        dto.setDate(
                production.getDate());

        dto.setPassQty(
                production.getPassQty());

        dto.setRejectQty(
                production.getRejectQty());

        dto.setRemarks(
                production.getRemarks());

        dto.setStyleNo(
                production.getStyleNo());

        dto.setBuyerName(
                production.getBuyerName());

        dto.setCreatedAt(production.getCreatedAt());

        return dto;
    }
}
