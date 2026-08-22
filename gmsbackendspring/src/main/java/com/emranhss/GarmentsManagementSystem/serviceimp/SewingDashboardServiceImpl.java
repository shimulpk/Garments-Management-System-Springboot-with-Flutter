package com.emranhss.GarmentsManagementSystem.serviceimp;

import com.emranhss.GarmentsManagementSystem.dto.response.RecentSewingPlanResponseDto;
import com.emranhss.GarmentsManagementSystem.dto.response.SewingDashboardResponseDto;
import com.emranhss.GarmentsManagementSystem.dto.response.TodaySewingResponseDto;
import com.emranhss.GarmentsManagementSystem.entity.DayWiseSewingProduction;
import com.emranhss.GarmentsManagementSystem.entity.SewingPlan;
import com.emranhss.GarmentsManagementSystem.enums.SewingPlanStatus;
import com.emranhss.GarmentsManagementSystem.repository.DayWiseSewingProductionRepository;
import com.emranhss.GarmentsManagementSystem.repository.SewingPlanRepository;
import com.emranhss.GarmentsManagementSystem.service.SewingDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SewingDashboardServiceImpl implements SewingDashboardService {

    private final SewingPlanRepository sewingPlanRepository;

    private final DayWiseSewingProductionRepository sewingProductionRepository;

    @Override
    public SewingDashboardResponseDto getDashboard() {
        SewingDashboardResponseDto dto =
                new SewingDashboardResponseDto();

        // =====================================
        // Summary
        // =====================================

        dto.setTodaySewing(

                sewingProductionRepository
                        .getTodaySewing(LocalDate.now())
                        .longValue()

        );

        dto.setTodayReject(

                sewingProductionRepository
                        .getTodayReject(LocalDate.now())
                        .longValue()

        );

        dto.setTotalPlans(

                sewingPlanRepository.count()

        );

        dto.setPendingPlans(

                sewingPlanRepository
                        .countByStatus(SewingPlanStatus.PENDING)

        );

        dto.setCompletedPlans(

                sewingPlanRepository
                        .countByStatus(SewingPlanStatus.COMPLETED)

        );

        // =====================================
        // Today's Sewing
        // =====================================

        List<TodaySewingResponseDto> todayList =
                new ArrayList<>();

        List<DayWiseSewingProduction> productions =
                sewingProductionRepository
                        .getTodayProductions(LocalDate.now());

        for (DayWiseSewingProduction production : productions) {

            todayList.add(

                    new TodaySewingResponseDto(

                            production.getStyleNo(),

                            production.getOrderNo(),

                            production.getAchievedQuantity() == null
                                    ? 0L
                                    : production.getAchievedQuantity().longValue(),

                            production.getRejectionQty() == null
                                    ? 0L
                                    : production.getRejectionQty().longValue()

                    )

            );

        }

        dto.setTodaySewings(todayList);

        // =====================================
        // Recent Plans
        // =====================================

        List<RecentSewingPlanResponseDto> recentPlans =
                new ArrayList<>();

        List<SewingPlan> plans =
                sewingPlanRepository
                        .findTop5ByOrderByStartDateDesc();

        for (SewingPlan plan : plans) {

            recentPlans.add(

                    new RecentSewingPlanResponseDto(

                            plan.getSewingPlanId(),

                            plan.getStyleNo(),

                            plan.getOrderNo(),

                            plan.getOutputQty(),

                            plan.getStatus()

                    )

            );

        }

        dto.setRecentPlans(recentPlans);

        return dto;

    }

}

