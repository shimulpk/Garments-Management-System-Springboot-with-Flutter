package com.emranhss.GarmentsManagementSystem.serviceimp;

import com.emranhss.GarmentsManagementSystem.dto.response.ProductionDashboardResponseDto;
import com.emranhss.GarmentsManagementSystem.dto.response.TodayProductionResponseDto;
import com.emranhss.GarmentsManagementSystem.repository.*;
import com.emranhss.GarmentsManagementSystem.service.ProductionDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductionDashboardServiceImpl implements ProductionDashboardService {

    private final OrderRepository orderRepository;

    private final DayWiseCuttingProductionRepository cuttingRepository;

    private final DayWiseSewingProductionRepository sewingRepository;

    private final DayWiseFinishingProductionRepository finishingRepository;

    private final DayWisePackingProductionRepository packingRepository;

    @Override
    public ProductionDashboardResponseDto getDashboard() {
        ProductionDashboardResponseDto dto =
                new ProductionDashboardResponseDto();

        // ==========================================
        // Summary
        // ==========================================

        dto.setPendingOrders(
                orderRepository.countByStatus("PENDING")
        );

        dto.setConfirmedOrders(
                orderRepository.countByStatus("CONFIRMED")
        );

        dto.setTodayCutting(
                cuttingRepository
                        .getTodayCutting(LocalDate.now())
                        .longValue()
        );

        dto.setTodaySewing(
                sewingRepository
                        .getTodaySewing(LocalDate.now())
                        .longValue()
        );

        dto.setTodayFinishing(
                finishingRepository
                        .getTodayFinishing(LocalDate.now())
                        .longValue()
        );

        dto.setTodayPacking(
                packingRepository
                        .getTodayPacking(LocalDate.now())
                        .longValue()
        );

        // ==========================================
        // Today's Production
        // ==========================================

        List<TodayProductionResponseDto> productions =
                new ArrayList<>();

        productions.add(

                new TodayProductionResponseDto(

                        "Cutting",

                        dto.getTodayCutting()

                )

        );

        productions.add(

                new TodayProductionResponseDto(

                        "Sewing",

                        dto.getTodaySewing()

                )

        );

        productions.add(

                new TodayProductionResponseDto(

                        "Finishing",

                        dto.getTodayFinishing()

                )

        );

        productions.add(

                new TodayProductionResponseDto(

                        "Packing",

                        dto.getTodayPacking()

                )

        );

        dto.setTodayProductions(productions);

        return dto;

    }

}

