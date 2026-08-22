package com.emranhss.GarmentsManagementSystem.serviceimp;

import com.emranhss.GarmentsManagementSystem.dto.mapper.FinishingPlanMapper;
import com.emranhss.GarmentsManagementSystem.dto.mapper.SewingPlanMapper;
import com.emranhss.GarmentsManagementSystem.dto.request.FinishingPlanRequestDto;
import com.emranhss.GarmentsManagementSystem.dto.response.FinishingPlanResponseDto;
import com.emranhss.GarmentsManagementSystem.dto.response.SewingPlanResponseDto;
import com.emranhss.GarmentsManagementSystem.entity.FinishingPlan;
import com.emranhss.GarmentsManagementSystem.entity.SewingPlan;
import com.emranhss.GarmentsManagementSystem.enums.FinishingPlanStatus;
import com.emranhss.GarmentsManagementSystem.enums.SewingPlanStatus;
import com.emranhss.GarmentsManagementSystem.repository.FinishingPlanRepository;
import com.emranhss.GarmentsManagementSystem.repository.SewingPlanRepository;
import com.emranhss.GarmentsManagementSystem.service.FinishingPlanService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class FinishingPlanServiceImpl implements FinishingPlanService {

    private final FinishingPlanRepository finishingPlanRepository;

    private final SewingPlanRepository sewingPlanRepository;


    @Override
    public FinishingPlanResponseDto create(FinishingPlanRequestDto request) {

//          Load Sewing Plan

        SewingPlan sewingPlan =
                sewingPlanRepository.findById(
                                request.getSewingPlanId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Sewing Plan Not Found"));

//       Business Rule: Only COMPLETED Sewing Plan  can create Finishing Plan

        if (sewingPlan.getStatus()
                != SewingPlanStatus.COMPLETED) {

            throw new RuntimeException(
                    "Sewing Plan is not completed");
        }

        FinishingPlan finishingPlan =
                new FinishingPlan();

//        Auto Generate Plan Code

        finishingPlan.setFinishingPlanId(
                "FP-" + System.currentTimeMillis());

        finishingPlan.setSewingPlan(
                sewingPlan);

//         Auto Fill From Sewing Plan

        finishingPlan.setBuyerName(
                sewingPlan.getBuyerName());

        finishingPlan.setOrderNo(
                sewingPlan.getOrderNo());

        finishingPlan.setStyleNo(
                sewingPlan.getStyleNo());

        finishingPlan.setColor(
                sewingPlan.getColor());

//       Auto Input & Target Qty

        finishingPlan.setInputQty(
                sewingPlan.getOutputQty());

        finishingPlan.setTargetQty(
                sewingPlan.getOutputQty());

//         Initial Values

        finishingPlan.setPassQty(0);
        finishingPlan.setRejectionQty(0);

//       Process Flags

        finishingPlan.setProcTrimming(
                request.getProcTrimming());

        finishingPlan.setProcIroning(
                request.getProcIroning());

        finishingPlan.setProcWashing(
                request.getProcWashing());

        finishingPlan.setProcButtonAttach(
                request.getProcButtonAttach());

//       Other Information

        finishingPlan.setFinishingTableNo(
                request.getFinishingTableNo());

        finishingPlan.setSupervisorName(
                request.getSupervisorName());

        finishingPlan.setStartDate(
                request.getStartDate());

        finishingPlan.setEndDate(
                request.getEndDate());

//         Initial Status

        finishingPlan.setStatus(
                FinishingPlanStatus.IN_PROGRESS);

        FinishingPlan saved =
                finishingPlanRepository.save(
                        finishingPlan);

        return FinishingPlanMapper.toDto(
                saved);
    }

    @Override
    public FinishingPlanResponseDto update(Long id, FinishingPlanRequestDto request) {
        FinishingPlan finishingPlan =
                finishingPlanRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Finishing Plan Not Found"));

        finishingPlan.setProcTrimming(
                request.getProcTrimming());

        finishingPlan.setProcIroning(
                request.getProcIroning());

        finishingPlan.setProcWashing(
                request.getProcWashing());

        finishingPlan.setProcButtonAttach(
                request.getProcButtonAttach());

        finishingPlan.setFinishingTableNo(
                request.getFinishingTableNo());

        finishingPlan.setSupervisorName(
                request.getSupervisorName());

        finishingPlan.setStartDate(
                request.getStartDate());

        finishingPlan.setEndDate(
                request.getEndDate());

        FinishingPlan updated =
                finishingPlanRepository.save(
                        finishingPlan);

        return FinishingPlanMapper.toDto(
                updated);
    }

    @Override
    public FinishingPlanResponseDto getById(Long id) {
        FinishingPlan finishingPlan =
                finishingPlanRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Finishing Plan Not Found"));

        return FinishingPlanMapper.toDto(
                finishingPlan);
    }

    @Override
    public List<FinishingPlanResponseDto> getAll() {
        return finishingPlanRepository.findAll()
                .stream()
                .map(
                        FinishingPlanMapper::toDto)
                .toList();
    }

    @Override
    public void delete(Long id) {
        FinishingPlan finishingPlan =
                finishingPlanRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Finishing Plan Not Found"));

        finishingPlanRepository.delete(
                finishingPlan);
    }

    @Override
    public List<SewingPlanResponseDto> getAvailableSewingPlans() {

        return sewingPlanRepository
                .findByStatus(SewingPlanStatus.COMPLETED)
                .stream()
                .filter(plan ->
                        !finishingPlanRepository.existsBySewingPlanId(plan.getId()))
                .map(SewingPlanMapper::toDto)
                .toList();

    }
}
