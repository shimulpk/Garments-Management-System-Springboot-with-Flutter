package com.emranhss.GarmentsManagementSystem.dto.mapper;

import com.emranhss.GarmentsManagementSystem.dto.request.DayWiseCuttingProductionRequestDto;
import com.emranhss.GarmentsManagementSystem.dto.response.DayWiseCuttingProductionResponseDto;
import com.emranhss.GarmentsManagementSystem.entity.DayWiseCuttingProduction;

public class DayWiseCuttingProductionMapper {
    public static DayWiseCuttingProduction toEntity(
            DayWiseCuttingProductionRequestDto dto) {

        DayWiseCuttingProduction entity =
                new DayWiseCuttingProduction();

        entity.setDate(dto.getDate());
        entity.setActualCutPieces(dto.getActualCutPieces());
        entity.setRejectPieces(dto.getRejectPieces());

        return entity;
    }

    public static DayWiseCuttingProductionResponseDto toDto(
            DayWiseCuttingProduction entity) {

        DayWiseCuttingProductionResponseDto dto =
                new DayWiseCuttingProductionResponseDto();

        dto.setId(entity.getId());

        dto.setCuttingPlanId(
                entity.getCuttingPlan().getId());

        dto.setCuttingPlanCode(
                entity.getCuttingPlan().getCuttingPlanId());

        dto.setDate(entity.getDate());

        dto.setActualCutPieces(
                entity.getActualCutPieces());

        dto.setRejectPieces(
                entity.getRejectPieces());

        dto.setStyleNo(
                entity.getStyleNo());

        dto.setCuttingMaster(
                entity.getCuttingMaster());

        dto.setCreatedAt(entity.getCreatedAt());

        return dto;
    }
}
