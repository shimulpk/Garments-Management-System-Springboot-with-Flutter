package com.emranhss.GarmentsManagementSystem.service;


import com.emranhss.GarmentsManagementSystem.dto.request.DayWiseFinishingProductionRequestDto;
import com.emranhss.GarmentsManagementSystem.dto.response.DayWiseFinishingProductionResponseDto;
import com.emranhss.GarmentsManagementSystem.dto.response.FinishingProgressResponseDto;

import java.util.List;

public interface DayWiseFinishingProductionService {


    DayWiseFinishingProductionResponseDto create(
            DayWiseFinishingProductionRequestDto request);


    DayWiseFinishingProductionResponseDto update(
            Long id,
            DayWiseFinishingProductionRequestDto request);


    DayWiseFinishingProductionResponseDto getById(
            Long id);


    List<DayWiseFinishingProductionResponseDto> getAll();


    void delete(Long id);

//     Angular Summary Card Total Pass So Far,  Target Quantity, Remaining

    FinishingProgressResponseDto getProgress(
            Long finishingPlanId);


    List<DayWiseFinishingProductionResponseDto> getByFinishingPlan(
            Long finishingPlanId);
}
