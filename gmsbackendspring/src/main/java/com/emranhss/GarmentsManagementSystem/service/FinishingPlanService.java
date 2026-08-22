package com.emranhss.GarmentsManagementSystem.service;

import com.emranhss.GarmentsManagementSystem.dto.request.FinishingPlanRequestDto;
import com.emranhss.GarmentsManagementSystem.dto.response.FinishingPlanResponseDto;
import com.emranhss.GarmentsManagementSystem.dto.response.SewingPlanResponseDto;

import java.util.List;

public interface FinishingPlanService {


    FinishingPlanResponseDto create(
            FinishingPlanRequestDto request);


    FinishingPlanResponseDto update(
            Long id,
            FinishingPlanRequestDto request);


    FinishingPlanResponseDto getById(
            Long id);

    List<FinishingPlanResponseDto> getAll();


    void delete(Long id);

    List<SewingPlanResponseDto> getAvailableSewingPlans();
}
