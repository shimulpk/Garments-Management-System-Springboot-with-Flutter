package com.emranhss.GarmentsManagementSystem.service;

import com.emranhss.GarmentsManagementSystem.dto.request.PackingPlanRequestDto;
import com.emranhss.GarmentsManagementSystem.dto.response.FinishingPlanResponseDto;
import com.emranhss.GarmentsManagementSystem.dto.response.PackingPlanResponseDto;

import java.util.List;

public interface PackingPlanService {


    PackingPlanResponseDto create(
            PackingPlanRequestDto request);


    PackingPlanResponseDto update(
            Long id,
            PackingPlanRequestDto request);


    PackingPlanResponseDto getById(
            Long id);


    List<PackingPlanResponseDto> getAll();


    void delete(Long id);

    List<FinishingPlanResponseDto> getAvailableFinishingPlans();

    List<PackingPlanResponseDto> getAvailableForDayWisePacking();
}
