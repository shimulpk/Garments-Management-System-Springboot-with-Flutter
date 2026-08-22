package com.emranhss.GarmentsManagementSystem.serviceimp;

import com.emranhss.GarmentsManagementSystem.dto.mapper.SewingPlanMapper;
import com.emranhss.GarmentsManagementSystem.dto.request.SewingPlanRequestDto;
import com.emranhss.GarmentsManagementSystem.dto.request.SewingTargetRequestDto;
import com.emranhss.GarmentsManagementSystem.dto.response.SewingLineProgressResponseDto;
import com.emranhss.GarmentsManagementSystem.dto.response.SewingPlanResponseDto;
import com.emranhss.GarmentsManagementSystem.entity.CuttingPlan;
import com.emranhss.GarmentsManagementSystem.entity.ProductionLine;
import com.emranhss.GarmentsManagementSystem.entity.SewingPlan;
import com.emranhss.GarmentsManagementSystem.entity.SewingTarget;
import com.emranhss.GarmentsManagementSystem.enums.CuttingPlanStatus;
import com.emranhss.GarmentsManagementSystem.enums.SewingPlanStatus;
import com.emranhss.GarmentsManagementSystem.repository.CuttingPlanRepository;
import com.emranhss.GarmentsManagementSystem.repository.DayWiseSewingProductionRepository;
import com.emranhss.GarmentsManagementSystem.repository.ProductionLineRepository;
import com.emranhss.GarmentsManagementSystem.repository.SewingPlanRepository;
import com.emranhss.GarmentsManagementSystem.service.SewingPlanService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SewingPlanServiceImpl implements SewingPlanService {
    private final SewingPlanRepository sewingPlanRepository;

    private final CuttingPlanRepository cuttingPlanRepository;

    private final ProductionLineRepository productionLineRepository;
    private final DayWiseSewingProductionRepository dayWiseSewingProductionRepository;

    @Override
    public SewingPlanResponseDto create(SewingPlanRequestDto request) {

//          Load Cutting Plan

        CuttingPlan cuttingPlan =
                cuttingPlanRepository
                        .findById(
                                request.getCuttingPlanId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Cutting Plan Not Found"));


//         sudhu COMPLETED Cutting Plan
//         sewing plan hobe

        if (cuttingPlan.getStatus()
                != CuttingPlanStatus.COMPLETED) {

            throw new RuntimeException(
                    "Cutting Plan is not completed");
        }

        SewingPlan sewingPlan =
                new SewingPlan();


//         Auto Generated Plan Id

        sewingPlan.setSewingPlanId(
                "SP-" + System.currentTimeMillis());

        sewingPlan.setCuttingPlan(cuttingPlan);


//         Auto Fill From Cutting Plan

        sewingPlan.setBuyerName(
                cuttingPlan.getBuyer()
                        .getBuyerName());

        sewingPlan.setOrderNo(
                cuttingPlan.getOrder()
                        .getOrderId());

        sewingPlan.setStyleNo(
                cuttingPlan.getStyleNo());

        sewingPlan.setColor(
                cuttingPlan.getColor());


//         Total completed cutting qty

        sewingPlan.setInputReceivedQty(
                cuttingPlan.getPlannedPieces());


//         Initially no output

        sewingPlan.setOutputQty(0);


//          Initially no rejection

        sewingPlan.setRejectionQty(0);

        sewingPlan.setStartDate(
                request.getStartDate());

        sewingPlan.setEndDate(
                request.getEndDate());

        /*
         * Auto Status
         */
        sewingPlan.setStatus(
                SewingPlanStatus.IN_SEWING);

        /*
         * Save first
         */
        SewingPlan savedPlan =
                sewingPlanRepository.save(
                        sewingPlan);

        /*
         * Save Line Targets
         */
        for (SewingTargetRequestDto targetDto
                : request.getTargets()) {

            ProductionLine line =
                    productionLineRepository
                            .findById(
                                    targetDto.getProductionLineId())
                            .orElseThrow(() ->
                                    new RuntimeException(
                                            "Production Line Not Found"));

            SewingTarget target =
                    new SewingTarget();

            target.setProductionLine(line);

            target.setTargetQuantity(
                    targetDto.getTargetQuantity());

            target.setSewingPlan(savedPlan);

            savedPlan.getTargets()
                    .add(target);
        }

        savedPlan =
                sewingPlanRepository.save(
                        savedPlan);

        return SewingPlanMapper.toDto(
                savedPlan);
    }

    @Override
    public SewingPlanResponseDto update(Long id, SewingPlanRequestDto request) {
        SewingPlan sewingPlan =
                sewingPlanRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Sewing Plan Not Found"));

        sewingPlan.setStartDate(
                request.getStartDate());

        sewingPlan.setEndDate(
                request.getEndDate());

        return SewingPlanMapper.toDto(
                sewingPlanRepository.save(
                        sewingPlan));
    }

    @Override
    public SewingPlanResponseDto getById(Long id) {
        SewingPlan sewingPlan =
                sewingPlanRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Sewing Plan Not Found"));

        SewingPlanResponseDto dto =
                SewingPlanMapper.toDto(sewingPlan);

        buildLineProgress(dto, sewingPlan);

        return dto;
    }

    @Override
    public List<SewingPlanResponseDto> getAll() {
        return sewingPlanRepository.findAll()

                .stream()

                .map(plan -> {

                    SewingPlanResponseDto dto =
                            SewingPlanMapper.toDto(plan);

                    buildLineProgress(dto, plan);

                    return dto;

                })

                .toList();
    }

    @Override
    public void delete(Long id) {
        sewingPlanRepository.deleteById(id);
    }

    @Override
    public List<SewingPlanResponseDto> getAvailableForDayWiseProduction() {
        return sewingPlanRepository.findAll()
                .stream()
                .filter(plan ->
                        plan.getStatus() != SewingPlanStatus.COMPLETED)
                .map(SewingPlanMapper::toDto)
                .toList();
    }


    private void buildLineProgress(SewingPlanResponseDto dto,
                                   SewingPlan sewingPlan) {

        List<SewingLineProgressResponseDto> progressList =
                new ArrayList<>();

        for (SewingTarget target : sewingPlan.getTargets()) {

            SewingLineProgressResponseDto progress =
                    new SewingLineProgressResponseDto();

            progress.setProductionLineId(
                    target.getProductionLine().getId());

            progress.setLineId(
                    target.getProductionLine().getLineId());

            progress.setTargetQty(
                    target.getTargetQuantity());

            Integer achieved =
                    dayWiseSewingProductionRepository
                            .getAchievedQty(
                                    sewingPlan.getId(),
                                    target.getProductionLine().getId());

            Integer reject =
                    dayWiseSewingProductionRepository
                            .getRejectQty(
                                    sewingPlan.getId(),
                                    target.getProductionLine().getId());

            progress.setAchievedQty(achieved);

            progress.setRejectQty(reject);

            progress.setRemainingQty(
                    Math.max(
                            target.getTargetQuantity() - achieved,
                            0));

            progressList.add(progress);
        }

        dto.setLineProgress(progressList);
    }
}
