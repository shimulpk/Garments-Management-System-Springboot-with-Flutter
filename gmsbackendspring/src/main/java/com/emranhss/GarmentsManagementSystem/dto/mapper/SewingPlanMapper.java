package com.emranhss.GarmentsManagementSystem.dto.mapper;

import com.emranhss.GarmentsManagementSystem.dto.response.SewingPlanResponseDto;
import com.emranhss.GarmentsManagementSystem.dto.response.SewingTargetResponseDto;
import com.emranhss.GarmentsManagementSystem.entity.SewingPlan;
import com.emranhss.GarmentsManagementSystem.entity.SewingTarget;

import java.util.ArrayList;
import java.util.List;

public class SewingPlanMapper {
    public static SewingPlanResponseDto toDto(
            SewingPlan sewingPlan) {

        if (sewingPlan == null) {
            return null;
        }

        SewingPlanResponseDto dto =
                new SewingPlanResponseDto();

        dto.setId(sewingPlan.getId());

        dto.setSewingPlanId(
                sewingPlan.getSewingPlanId());

        dto.setCuttingPlanId(
                sewingPlan.getCuttingPlan().getId());

        dto.setBuyerName(
                sewingPlan.getBuyerName());

        dto.setOrderNo(
                sewingPlan.getOrderNo());

        dto.setStyleNo(
                sewingPlan.getStyleNo());

        dto.setColor(
                sewingPlan.getColor());

        dto.setInputReceivedQty(
                sewingPlan.getInputReceivedQty());

        dto.setOutputQty(
                sewingPlan.getOutputQty());

        dto.setRejectionQty(
                sewingPlan.getRejectionQty());

        dto.setStartDate(
                sewingPlan.getStartDate());

        dto.setEndDate(
                sewingPlan.getEndDate());

        dto.setStatus(
                sewingPlan.getStatus());

        List<SewingTargetResponseDto> targets =
                sewingPlan.getTargets()
                        .stream()
                        .map(SewingPlanMapper::targetToDto)
                        .toList();

        dto.setTargets(targets);

        dto.setLineProgress(
                new ArrayList<>());

        return dto;
    }

    private static SewingTargetResponseDto targetToDto(
            SewingTarget target) {

        SewingTargetResponseDto dto =
                new SewingTargetResponseDto();

        dto.setId(target.getId());

        dto.setProductionLineId(
                target.getProductionLine().getId());

        dto.setLineId(
                target.getProductionLine().getLineId());

        dto.setTargetQuantity(
                target.getTargetQuantity());

        return dto;
    }
}
