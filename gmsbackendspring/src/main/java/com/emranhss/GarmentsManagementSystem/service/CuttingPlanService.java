package com.emranhss.GarmentsManagementSystem.service;

import com.emranhss.GarmentsManagementSystem.dto.request.CuttingPlanRequestDto;
import com.emranhss.GarmentsManagementSystem.dto.response.CuttingPlanResponseDto;

import java.util.List;

public interface CuttingPlanService {
    CuttingPlanResponseDto create(
            CuttingPlanRequestDto request);

    CuttingPlanResponseDto update(
            Long id,
            CuttingPlanRequestDto request);

    CuttingPlanResponseDto getById(Long id);

    List<CuttingPlanResponseDto> getAll();

    void delete(Long id);

    List<CuttingPlanResponseDto> getPendingPlans();

    List<CuttingPlanResponseDto> getCompletedPlans();

    List<CuttingPlanResponseDto> getAvailableForSewingPlan();
}
