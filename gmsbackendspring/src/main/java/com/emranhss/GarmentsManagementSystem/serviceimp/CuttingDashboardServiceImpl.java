package com.emranhss.GarmentsManagementSystem.serviceimp;

import com.emranhss.GarmentsManagementSystem.dto.response.CuttingDashboardResponseDto;
import com.emranhss.GarmentsManagementSystem.dto.response.RecentCuttingPlanResponseDto;
import com.emranhss.GarmentsManagementSystem.dto.response.TodayCuttingResponseDto;
import com.emranhss.GarmentsManagementSystem.entity.CuttingPlan;
import com.emranhss.GarmentsManagementSystem.entity.DayWiseCuttingProduction;
import com.emranhss.GarmentsManagementSystem.enums.CuttingPlanStatus;
import com.emranhss.GarmentsManagementSystem.repository.CuttingPlanRepository;
import com.emranhss.GarmentsManagementSystem.repository.DayWiseCuttingProductionRepository;
import com.emranhss.GarmentsManagementSystem.service.CuttingDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CuttingDashboardServiceImpl implements CuttingDashboardService {

    private final CuttingPlanRepository cuttingPlanRepository;

    private final DayWiseCuttingProductionRepository cuttingProductionRepository;

    @Override
    public CuttingDashboardResponseDto getDashboard() {
        CuttingDashboardResponseDto dto =
                new CuttingDashboardResponseDto();

        // ======================================
        // Summary
        // ======================================

        dto.setTodayCutting(

                cuttingProductionRepository
                        .getTodayCutting(LocalDate.now())
                        .longValue()

        );

        dto.setTodayReject(

                cuttingProductionRepository
                        .getTodayReject(LocalDate.now())
                        .longValue()

        );

        dto.setTotalPlans(

                cuttingPlanRepository.count()

        );

        dto.setPendingPlans(

                cuttingPlanRepository
                        .countByStatus(CuttingPlanStatus.PENDING)

        );

        dto.setCompletedPlans(

                cuttingPlanRepository
                        .countByStatus(CuttingPlanStatus.COMPLETED)

        );

        // ======================================
        // Today's Cutting
        // ======================================

        List<TodayCuttingResponseDto> todayList =
                new ArrayList<>();

        List<DayWiseCuttingProduction> productions =
                cuttingProductionRepository
                        .getTodayProductions(LocalDate.now());

        for (DayWiseCuttingProduction production : productions) {

            todayList.add(

                    new TodayCuttingResponseDto(

                            production.getStyleNo(),

                            production.getCuttingMaster(),

                            production.getActualCutPieces() == null
                                    ? 0L
                                    : production.getActualCutPieces().longValue(),

                            production.getRejectPieces() == null
                                    ? 0L
                                    : production.getRejectPieces().longValue()

                    )

            );

        }

        dto.setTodayCuttings(todayList);

        // ======================================
        // Recent Plans
        // ======================================

        List<RecentCuttingPlanResponseDto> recentPlans =
                new ArrayList<>();

        List<CuttingPlan> plans =
                cuttingPlanRepository
                        .findTop5ByOrderByStartDateDesc();

        for (CuttingPlan plan : plans) {

            recentPlans.add(

                    new RecentCuttingPlanResponseDto(

                            plan.getCuttingPlanId(),

                            plan.getStyleNo(),

                            plan.getOrder().getOrderId(),

                            plan.getPlannedPieces(),

                            plan.getStatus()

                    )

            );

        }

        dto.setRecentPlans(recentPlans);

        return dto;

    }

}

