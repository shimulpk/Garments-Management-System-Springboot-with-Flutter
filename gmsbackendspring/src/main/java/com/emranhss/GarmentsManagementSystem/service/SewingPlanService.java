package com.emranhss.GarmentsManagementSystem.service;

import com.emranhss.GarmentsManagementSystem.dto.request.SewingPlanRequestDto;
import com.emranhss.GarmentsManagementSystem.dto.response.SewingPlanResponseDto;

import java.util.List;

public interface SewingPlanService {
    SewingPlanResponseDto create(
            SewingPlanRequestDto request);

    SewingPlanResponseDto update(
            Long id,
            SewingPlanRequestDto request);

    SewingPlanResponseDto getById(Long id);

    List<SewingPlanResponseDto> getAll();

    void delete(Long id);

    List<SewingPlanResponseDto> getAvailableForDayWiseProduction();
}
