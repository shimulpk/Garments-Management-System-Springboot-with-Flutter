package com.emranhss.GarmentsManagementSystem.dto.mapper;

import com.emranhss.GarmentsManagementSystem.dto.request.CuttingPlanRequestDto;
import com.emranhss.GarmentsManagementSystem.dto.response.CuttingPlanResponseDto;
import com.emranhss.GarmentsManagementSystem.entity.CuttingPlan;

public class CuttingPlanMapper {

    public static CuttingPlan toEntity(
            CuttingPlanRequestDto dto) {

        if (dto == null) {
            return null;
        }

        CuttingPlan entity = new CuttingPlan();

        entity.setFabricType(
                dto.getFabricType());

        entity.setColor(
                dto.getColor());

        entity.setMarkerLength(
                dto.getMarkerLength());

        entity.setMarkerWidth(
                dto.getMarkerWidth());

        entity.setNumberOfPlies(
                dto.getNumberOfPlies());

        entity.setMarkerEfficiency(
                dto.getMarkerEfficiency());

        entity.setCuttingTableNumber(
                dto.getCuttingTableNumber());

        entity.setCuttingMaster(
                dto.getCuttingMaster());

        entity.setStartDate(
                dto.getStartDate());

        entity.setEndDate(
                dto.getEndDate());

        return entity;
    }

    public static CuttingPlanResponseDto toDto(
            CuttingPlan entity) {

        if (entity == null) {
            return null;
        }

        CuttingPlanResponseDto dto =
                new CuttingPlanResponseDto();

        dto.setId(entity.getId());

        dto.setCuttingPlanId(
                entity.getCuttingPlanId());

        dto.setStyleNo(
                entity.getStyleNo());

        dto.setFabricType(
                entity.getFabricType());

        dto.setColor(
                entity.getColor());

        dto.setTotalFabricRequired(
                entity.getTotalFabricRequired());

        dto.setMarkerLength(
                entity.getMarkerLength());

        dto.setMarkerWidth(
                entity.getMarkerWidth());

        dto.setNumberOfPlies(
                entity.getNumberOfPlies());

        dto.setMarkerEfficiency(
                entity.getMarkerEfficiency());

        dto.setPlannedPieces(
                entity.getPlannedPieces());

        dto.setCuttingTableNumber(
                entity.getCuttingTableNumber());

        dto.setCuttingMaster(
                entity.getCuttingMaster());

        dto.setStartDate(
                entity.getStartDate());

        dto.setEndDate(
                entity.getEndDate());

        dto.setStatus(
                entity.getStatus());

        if (entity.getBuyer() != null) {

            dto.setBuyerId(
                    entity.getBuyer().getId());

            dto.setBuyerCode(
                    entity.getBuyer().getBuyerCode());

            dto.setBuyerName(
                    entity.getBuyer().getBuyerName());
        }

        if (entity.getOrder() != null) {

            dto.setOrderDbId(
                    entity.getOrder().getId());

            dto.setOrderId(
                    entity.getOrder().getOrderId());

            dto.setPoNumber(
                    entity.getOrder().getPoNumber());
        }

        return dto;
    }
}
