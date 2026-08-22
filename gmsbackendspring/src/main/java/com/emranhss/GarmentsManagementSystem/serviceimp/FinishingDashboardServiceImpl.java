package com.emranhss.GarmentsManagementSystem.serviceimp;

import com.emranhss.GarmentsManagementSystem.dto.response.FinishingDashboardResponseDto;
import com.emranhss.GarmentsManagementSystem.dto.response.RecentFinishingPlanResponseDto;
import com.emranhss.GarmentsManagementSystem.dto.response.TodayFinishingResponseDto;
import com.emranhss.GarmentsManagementSystem.entity.DayWiseFinishingProduction;
import com.emranhss.GarmentsManagementSystem.entity.FinishingPlan;
import com.emranhss.GarmentsManagementSystem.enums.FinishingPlanStatus;
import com.emranhss.GarmentsManagementSystem.repository.DayWiseFinishingProductionRepository;
import com.emranhss.GarmentsManagementSystem.repository.FinishingPlanRepository;
import com.emranhss.GarmentsManagementSystem.service.FinishingDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FinishingDashboardServiceImpl implements FinishingDashboardService {

    private final FinishingPlanRepository finishingPlanRepository;

    private final DayWiseFinishingProductionRepository finishingProductionRepository;

    @Override
    public FinishingDashboardResponseDto getDashboard() {
        FinishingDashboardResponseDto dto =
                new FinishingDashboardResponseDto();

        // =====================================
        // Summary
        // =====================================

        dto.setTodayFinished(

                finishingProductionRepository
                        .getTodayFinishing(LocalDate.now())
                        .longValue()

        );

        dto.setTodayReject(

                finishingProductionRepository
                        .getTodayReject(LocalDate.now())
                        .longValue()

        );

        dto.setTotalPlans(

                finishingPlanRepository.count()

        );

        dto.setPendingPlans(

                finishingPlanRepository
                        .countByStatus(FinishingPlanStatus.IN_PROGRESS)

        );

        dto.setCompletedPlans(

                finishingPlanRepository
                        .countByStatus(FinishingPlanStatus.COMPLETED)

        );

        // =====================================
        // Today's Finishing
        // =====================================

        List<TodayFinishingResponseDto> todayList =
                new ArrayList<>();

        List<DayWiseFinishingProduction> productions =
                finishingProductionRepository
                        .getTodayProductions(LocalDate.now());

        for (DayWiseFinishingProduction production : productions) {

            todayList.add(

                    new TodayFinishingResponseDto(

                            production.getStyleNo(),

                            production.getBuyerName(),

                            production.getPassQty() == null
                                    ? 0L
                                    : production.getPassQty().longValue(),

                            production.getRejectQty() == null
                                    ? 0L
                                    : production.getRejectQty().longValue()

                    )

            );

        }

        dto.setTodayFinishings(todayList);

        // =====================================
        // Recent Plans
        // =====================================

        List<RecentFinishingPlanResponseDto> recentPlans =
                new ArrayList<>();

        List<FinishingPlan> plans =
                finishingPlanRepository
                        .findTop5ByOrderByStartDateDesc();

        for (FinishingPlan plan : plans) {

            recentPlans.add(

                    new RecentFinishingPlanResponseDto(

                            plan.getFinishingPlanId(),

                            plan.getStyleNo(),

                            plan.getBuyerName(),

                            plan.getTargetQty(),

                            plan.getStatus()

                    )

            );

        }

        dto.setRecentPlans(recentPlans);

        return dto;

    }

}

