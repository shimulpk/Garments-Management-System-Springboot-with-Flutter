package com.emranhss.GarmentsManagementSystem.service;

import com.emranhss.GarmentsManagementSystem.dto.request.DayWiseCuttingProductionRequestDto;
import com.emranhss.GarmentsManagementSystem.dto.request.DayWiseCuttingProductionUpdateRequestDto;
import com.emranhss.GarmentsManagementSystem.dto.response.*;

import java.time.LocalDate;
import java.util.List;

public interface DayWiseCuttingProductionService {

    DayWiseCuttingProductionResponseDto create(
            DayWiseCuttingProductionRequestDto request);

    DayWiseCuttingProductionResponseDto getById(Long id);

    List<DayWiseCuttingProductionResponseDto> getAll();

    void delete(Long id);

    CuttingPlanProgressResponseDto getProgress(
            Long cuttingPlanId);


    List<DayWiseCuttingProductionResponseDto> getByCuttingPlan(Long cuttingPlanId);


//    For android use
    List<DayWiseCuttingHistoryResponseDto> getHistory();

//    For android use
    List<DayWiseCuttingHistoryResponseDto> getHistory(LocalDate date);


//    For android use
    List<DayWiseCuttingHistoryDetailsResponseDto> getHistoryDetails(
            Long cuttingPlanId,
            LocalDate date
    );



// for android use
    DayWiseCuttingProductionResponseDto update(
            Long id,
            DayWiseCuttingProductionUpdateRequestDto requestDto
    );

    // for android use
    DayWiseCuttingHistorySummaryResponseDto getHistorySummary(
            Long cuttingPlanId,
            LocalDate date
    );




}


