package com.emranhss.GarmentsManagementSystem.serviceimp;

import com.emranhss.GarmentsManagementSystem.dto.response.PackingDashboardResponseDto;
import com.emranhss.GarmentsManagementSystem.dto.response.RecentPackingPlanResponseDto;
import com.emranhss.GarmentsManagementSystem.dto.response.TodayPackingResponseDto;
import com.emranhss.GarmentsManagementSystem.entity.DayWisePackingProduction;
import com.emranhss.GarmentsManagementSystem.entity.PackingPlan;
import com.emranhss.GarmentsManagementSystem.enums.PackingPlanStatus;
import com.emranhss.GarmentsManagementSystem.repository.DayWisePackingProductionRepository;
import com.emranhss.GarmentsManagementSystem.repository.PackingPlanRepository;
import com.emranhss.GarmentsManagementSystem.service.PackingDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PackingDashboardServiceImpl implements PackingDashboardService {
    private final PackingPlanRepository packingPlanRepository;

    private final DayWisePackingProductionRepository packingProductionRepository;

    @Override
    public PackingDashboardResponseDto getDashboard() {
        PackingDashboardResponseDto dto =
                new PackingDashboardResponseDto();

        // =====================================
        // Summary
        // =====================================

        dto.setTodayPacked(

                packingProductionRepository
                        .getTodayPacked(LocalDate.now())
                        .longValue()

        );

        dto.setTodayReject(

                packingProductionRepository
                        .getTodayReject(LocalDate.now())
                        .longValue()

        );

        dto.setTotalPlans(

                packingPlanRepository.count()

        );

        dto.setPendingPlans(

                packingPlanRepository
                        .countByStatus(PackingPlanStatus.PENDING)

        );

        dto.setCompletedPlans(

                packingPlanRepository
                        .countByStatus(PackingPlanStatus.READY_TO_SHIP)

        );

        // =====================================
        // Today's Packing
        // =====================================

        List<TodayPackingResponseDto> todayList =
                new ArrayList<>();

        List<DayWisePackingProduction> productions =
                packingProductionRepository
                        .getTodayProductions(LocalDate.now());

        for (DayWisePackingProduction production : productions) {

            todayList.add(

                    new TodayPackingResponseDto(

                            production.getStyleNo(),

                            production.getBuyerName(),

                            production.getOrderNo(),

                            production.getTodayPackedQty() == null
                                    ? 0L
                                    : production.getTodayPackedQty().longValue(),

                            production.getTodayRejectQty() == null
                                    ? 0L
                                    : production.getTodayRejectQty().longValue()

                    )

            );

        }

        dto.setTodayPackings(todayList);

        // =====================================
        // Recent Plans
        // =====================================

        List<RecentPackingPlanResponseDto> recentPlans =
                new ArrayList<>();

        List<PackingPlan> plans =
                packingPlanRepository
                        .findTop5ByOrderByStartDateDesc();

        for (PackingPlan plan : plans) {

            recentPlans.add(

                    new RecentPackingPlanResponseDto(


                            plan.getPackingPlanId(),

                            plan.getStyleNo(),

                            plan.getBuyerName(),

                            plan.getInputQty(),

                            plan.getStatus()

                    )

            );

        }

        dto.setRecentPlans(recentPlans);

        return dto;

    }

}

