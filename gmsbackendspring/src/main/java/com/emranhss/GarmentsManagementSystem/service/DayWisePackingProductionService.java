package com.emranhss.GarmentsManagementSystem.service;

import com.emranhss.GarmentsManagementSystem.dto.request.DayWisePackingProductionRequestDto;
import com.emranhss.GarmentsManagementSystem.dto.response.DayWisePackingProductionResponseDto;
import com.emranhss.GarmentsManagementSystem.dto.response.PackingPlanProgressResponseDto;
import com.emranhss.GarmentsManagementSystem.dto.response.PackingProductionSummaryResponseDto;

import java.util.List;

public interface DayWisePackingProductionService {


    DayWisePackingProductionResponseDto create(
            DayWisePackingProductionRequestDto request);


    DayWisePackingProductionResponseDto update(
            Long id,
            DayWisePackingProductionRequestDto request);


    DayWisePackingProductionResponseDto getById(
            Long id);


    List<DayWisePackingProductionResponseDto> getAll();


    void delete(Long id);

//    Angular Progress Card  Plan Input   Packed So Far Remaining

    PackingPlanProgressResponseDto getProgress(
            Long packingPlanId);

    List<DayWisePackingProductionResponseDto> getByPackingPlan(
            Long packingPlanId);


    List<PackingProductionSummaryResponseDto> getSummary();
}
