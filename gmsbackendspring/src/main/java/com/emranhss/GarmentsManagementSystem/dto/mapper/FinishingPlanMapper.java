package com.emranhss.GarmentsManagementSystem.dto.mapper;

import com.emranhss.GarmentsManagementSystem.dto.response.FinishingPlanResponseDto;
import com.emranhss.GarmentsManagementSystem.entity.FinishingPlan;

public class FinishingPlanMapper {

    public static FinishingPlanResponseDto toDto(
            FinishingPlan finishingPlan) {

        if (finishingPlan == null) {
            return null;
        }

        FinishingPlanResponseDto dto =
                new FinishingPlanResponseDto();

        dto.setId(
                finishingPlan.getId());

        dto.setFinishingPlanId(
                finishingPlan.getFinishingPlanId());

        dto.setSewingPlanId(
                finishingPlan.getSewingPlan()
                        .getId());

        dto.setSewingPlanCode(
                finishingPlan.getSewingPlan()
                        .getSewingPlanId());

        dto.setBuyerName(
                finishingPlan.getBuyerName());

        dto.setOrderNo(
                finishingPlan.getOrderNo());

        dto.setStyleNo(
                finishingPlan.getStyleNo());

        dto.setColor(
                finishingPlan.getColor());

        dto.setInputQty(
                finishingPlan.getInputQty());

        dto.setTargetQty(
                finishingPlan.getTargetQty());

        dto.setPassQty(
                finishingPlan.getPassQty());

        dto.setRejectionQty(
                finishingPlan.getRejectionQty());

        dto.setProcTrimming(
                finishingPlan.getProcTrimming());

        dto.setProcIroning(
                finishingPlan.getProcIroning());

        dto.setProcWashing(
                finishingPlan.getProcWashing());

        dto.setProcButtonAttach(
                finishingPlan.getProcButtonAttach());

        dto.setFinishingTableNo(
                finishingPlan.getFinishingTableNo());

        dto.setSupervisorName(
                finishingPlan.getSupervisorName());

        dto.setStartDate(
                finishingPlan.getStartDate());

        dto.setEndDate(
                finishingPlan.getEndDate());

        dto.setStatus(
                finishingPlan.getStatus());

        return dto;
    }
}
