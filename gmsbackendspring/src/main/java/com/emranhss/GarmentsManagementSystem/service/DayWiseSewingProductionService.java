package com.emranhss.GarmentsManagementSystem.service;

import com.emranhss.GarmentsManagementSystem.dto.request.DayWiseSewingProductionRequestDto;
import com.emranhss.GarmentsManagementSystem.dto.response.DayWiseSewingProductionResponseDto;
import com.emranhss.GarmentsManagementSystem.dto.response.LineWiseSewingProgressResponseDto;
import com.emranhss.GarmentsManagementSystem.dto.response.SewingPlanProgressResponseDto;


import java.util.List;


public interface DayWiseSewingProductionService {

    DayWiseSewingProductionResponseDto create(
            DayWiseSewingProductionRequestDto request);

    DayWiseSewingProductionResponseDto update(
            Long id,
            DayWiseSewingProductionRequestDto request);

    DayWiseSewingProductionResponseDto getById(
            Long id);

    List<DayWiseSewingProductionResponseDto>
    getAll();

    void delete(Long id);

    List<LineWiseSewingProgressResponseDto>
    getLineWiseProgress(Long sewingPlanId);

    List<DayWiseSewingProductionResponseDto> getBySewingPlan(Long sewingPlanId);

//    Used by Angular

//      Plan Input,Line Target,Achieved So Far,Remaining


    SewingPlanProgressResponseDto getProgress(
            Long sewingPlanId,
            Long productionLineId
    );
}
