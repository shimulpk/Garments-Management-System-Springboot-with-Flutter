package com.emranhss.GarmentsManagementSystem.dto.mapper;


import com.emranhss.GarmentsManagementSystem.dto.request.DayWiseSewingProductionRequestDto;
import com.emranhss.GarmentsManagementSystem.dto.response.DayWiseSewingProductionResponseDto;
import com.emranhss.GarmentsManagementSystem.entity.DayWiseSewingProduction;

public class DayWiseSewingProductionMapper {


    public static DayWiseSewingProductionResponseDto toDto(
            DayWiseSewingProduction production) {

        if (production == null) {
            return null;
        }
        DayWiseSewingProductionResponseDto responseDto = new DayWiseSewingProductionResponseDto();
        responseDto.setId(production.getId());
        responseDto.setSewingPlanId(production.getSewingPlan().getId());
        responseDto.setSewingPlanCode(production.getSewingPlan().getSewingPlanId());
        responseDto.setProductionLineId(production.getProductionLine().getId());
        responseDto.setLineId(production.getProductionLine().getLineId());
        responseDto.setDate(production.getDate());
        responseDto.setAchievedQuantity(production.getAchievedQuantity());
        responseDto.setRejectionQty(production.getRejectionQty());
        responseDto.setStyleNo(production.getStyleNo());
        responseDto.setOrderNo(production.getOrderNo());
        responseDto.setCreatedAt(production.getCreatedAt());

        return responseDto;

    }
}
