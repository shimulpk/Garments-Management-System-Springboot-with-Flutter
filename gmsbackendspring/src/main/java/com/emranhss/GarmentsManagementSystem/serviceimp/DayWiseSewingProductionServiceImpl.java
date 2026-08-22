package com.emranhss.GarmentsManagementSystem.serviceimp;

import com.emranhss.GarmentsManagementSystem.dto.mapper.DayWiseSewingProductionMapper;
import com.emranhss.GarmentsManagementSystem.dto.request.DayWiseSewingProductionRequestDto;
import com.emranhss.GarmentsManagementSystem.dto.response.DayWiseSewingProductionResponseDto;
import com.emranhss.GarmentsManagementSystem.dto.response.LineWiseSewingProgressResponseDto;
import com.emranhss.GarmentsManagementSystem.dto.response.SewingPlanProgressResponseDto;
import com.emranhss.GarmentsManagementSystem.entity.*;

import com.emranhss.GarmentsManagementSystem.enums.SewingPlanStatus;
import com.emranhss.GarmentsManagementSystem.repository.DayWiseSewingProductionRepository;
import com.emranhss.GarmentsManagementSystem.repository.OrderRepository;
import com.emranhss.GarmentsManagementSystem.repository.ProductionLineRepository;
import com.emranhss.GarmentsManagementSystem.repository.SewingPlanRepository;
import com.emranhss.GarmentsManagementSystem.service.DayWiseSewingProductionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DayWiseSewingProductionServiceImpl implements DayWiseSewingProductionService {

    private final DayWiseSewingProductionRepository dayWiseSewingProductionRepository;

    private final SewingPlanRepository sewingPlanRepository;

    private final ProductionLineRepository productionLineRepository;
    private final OrderRepository orderRepository;

   


    @Override
    public DayWiseSewingProductionResponseDto create(DayWiseSewingProductionRequestDto request) {

// Load Sewing Plan
        SewingPlan sewingPlan = sewingPlanRepository.findById(request.getSewingPlanId())
                .orElseThrow(() -> new RuntimeException("Sewing Plan Not Found"));

        // Load Production Line
        ProductionLine productionLine = productionLineRepository.findById(request.getProductionLineId())
                .orElseThrow(() -> new RuntimeException("Production Line Not Found"));


        // 1. Line-Wise Target Validation
        SewingTarget lineTarget = sewingPlan.getTargets().stream()
                .filter(t -> t.getProductionLine().getId().equals(productionLine.getId()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Target for this production line not found in Sewing Plan"));

        int currentLineAchieved = dayWiseSewingProductionRepository
                .findBySewingPlanIdAndProductionLineId(sewingPlan.getId(), productionLine.getId())
                .stream()
                .mapToInt(p -> p.getAchievedQuantity() == null ? 0 : p.getAchievedQuantity())
                .sum();

        int proposedLineTotal = currentLineAchieved + (request.getAchievedQuantity() == null ? 0 : request.getAchievedQuantity());

        if (proposedLineTotal > lineTarget.getTargetQuantity()) {
            throw new RuntimeException("Achieved quantity exceeds line target! Line Target: "
                    + lineTarget.getTargetQuantity() + ", Already Achieved: " + currentLineAchieved);
        }


        // 2. Overall Sewing Plan Target Validation
        int currentOverallAchieved = dayWiseSewingProductionRepository.findBySewingPlanId(sewingPlan.getId())
                .stream()
                .mapToInt(p -> p.getAchievedQuantity() == null ? 0 : p.getAchievedQuantity())
                .sum();

        int proposedOverallTotal = currentOverallAchieved + (request.getAchievedQuantity() == null ? 0 : request.getAchievedQuantity());

        if (proposedOverallTotal > sewingPlan.getInputReceivedQty()) {
            throw new RuntimeException("Total achieved quantity exceeds Sewing Plan input received quantity! Max Allowed: "
                    + sewingPlan.getInputReceivedQty() + ", Current Total: " + currentOverallAchieved);
        }

        // Save Entity
        DayWiseSewingProduction production = new DayWiseSewingProduction();
        production.setSewingPlan(sewingPlan);
        production.setProductionLine(productionLine);
        production.setDate(request.getDate());
        production.setAchievedQuantity(request.getAchievedQuantity());
        production.setRejectionQty(request.getRejectionQty());

        // Auto Fill From Sewing Plan
        production.setStyleNo(sewingPlan.getStyleNo());
        production.setOrderNo(sewingPlan.getOrderNo());
        production.setCreatedAt(java.time.LocalDateTime.now());

        DayWiseSewingProduction saved = dayWiseSewingProductionRepository.save(production);

        // Recalculate Sewing Plan Totals
        updateSewingPlanProgress(sewingPlan);

        return DayWiseSewingProductionMapper.toDto(saved);
    }

    @Override
    public DayWiseSewingProductionResponseDto update(Long id, DayWiseSewingProductionRequestDto request) {
        DayWiseSewingProduction production = dayWiseSewingProductionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Production Not Found"));

        SewingPlan sewingPlan = production.getSewingPlan();
        Long lineId = production.getProductionLine().getId();

        // =====================================
        // 1. Line-Wise Target Validation for Update
        // =====================================
        SewingTarget lineTarget = sewingPlan.getTargets().stream()
                .filter(t -> t.getProductionLine().getId().equals(lineId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Target for this production line not found"));

        int otherLineAchieved = dayWiseSewingProductionRepository
                .findBySewingPlanIdAndProductionLineId(sewingPlan.getId(), lineId)
                .stream()
                .filter(p -> !p.getId().equals(id)) // বর্তমান আপডেট হওয়া আইডি বাদ দেওয়া
                .mapToInt(p -> p.getAchievedQuantity() == null ? 0 : p.getAchievedQuantity())
                .sum();

        int proposedLineTotal = otherLineAchieved + (request.getAchievedQuantity() == null ? 0 : request.getAchievedQuantity());

        if (proposedLineTotal > lineTarget.getTargetQuantity()) {
            throw new RuntimeException("Update failed! Achieved quantity exceeds line target! Line Target: "
                    + lineTarget.getTargetQuantity() + ", Other Entries Total: " + otherLineAchieved);
        }

        // =====================================
        // 2. Overall Sewing Plan Target Validation for Update
        // =====================================
        int otherOverallAchieved = dayWiseSewingProductionRepository.findBySewingPlanId(sewingPlan.getId())
                .stream()
                .filter(p -> !p.getId().equals(id)) // বর্তমান আপডেট হওয়া আইডি বাদ দেওয়া
                .mapToInt(p -> p.getAchievedQuantity() == null ? 0 : p.getAchievedQuantity())
                .sum();

        int proposedOverallTotal = otherOverallAchieved + (request.getAchievedQuantity() == null ? 0 : request.getAchievedQuantity());

        if (proposedOverallTotal > sewingPlan.getInputReceivedQty()) {
            throw new RuntimeException("Update failed! Exceeds Sewing Plan input received quantity! Max Allowed: "
                    + sewingPlan.getInputReceivedQty() + ", Other Entries Total: " + otherOverallAchieved);
        }

        // Save Update
        production.setDate(request.getDate());
        production.setAchievedQuantity(request.getAchievedQuantity());
        production.setRejectionQty(request.getRejectionQty());

        DayWiseSewingProduction saved = dayWiseSewingProductionRepository.save(production);

        updateSewingPlanProgress(production.getSewingPlan());

        return DayWiseSewingProductionMapper.toDto(saved);
    }

    @Override
    public DayWiseSewingProductionResponseDto getById(Long id) {
        return DayWiseSewingProductionMapper
                .toDto(
                        dayWiseSewingProductionRepository
                                .findById(id)
                                .orElseThrow(() ->
                                        new RuntimeException(
                                                "Production Not Found")));
    }

    @Override
    public List<DayWiseSewingProductionResponseDto> getAll() {
        return dayWiseSewingProductionRepository
                .findAll()
                .stream()
                .map(
                        DayWiseSewingProductionMapper
                                ::toDto)
                .toList();
    }

    @Override
    public void delete(Long id) {
        DayWiseSewingProduction production = dayWiseSewingProductionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Production Not Found"));
        SewingPlan sewingPlan = production.getSewingPlan();

        // Data Deleted
        dayWiseSewingProductionRepository.delete(production);

        // Delete korar por main plan r total quantity reCalculate
        updateSewingPlanProgress(sewingPlan);
    }

    @Override
    public List<LineWiseSewingProgressResponseDto> getLineWiseProgress(Long sewingPlanId) {
        SewingPlan sewingPlan =
                sewingPlanRepository.findById(sewingPlanId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Sewing Plan Not Found"));

        List<LineWiseSewingProgressResponseDto> response =
                new ArrayList<>();

        for (SewingTarget target : sewingPlan.getTargets()) {

            Integer achieved =
                    dayWiseSewingProductionRepository
                            .getAchievedQty(
                                    sewingPlanId,
                                    target.getProductionLine().getId());

            Integer reject =
                    dayWiseSewingProductionRepository
                            .getRejectQty(
                                    sewingPlanId,
                                    target.getProductionLine().getId());

            LineWiseSewingProgressResponseDto dto =
                    new LineWiseSewingProgressResponseDto();

            dto.setProductionLineId(
                    target.getProductionLine().getId());

            dto.setLineId(
                    target.getProductionLine().getLineId());

            dto.setTargetQty(
                    target.getTargetQuantity());

            dto.setAchievedQty(
                    achieved);

            dto.setRejectQty(
                    reject);

            dto.setRemainingQty(
                    Math.max(
                            target.getTargetQuantity() - achieved,
                            0));

            response.add(dto);

        }

        return response;

    }

    @Override
    public List<DayWiseSewingProductionResponseDto> getBySewingPlan(Long sewingPlanId) {

        System.out.println("===== getBySewingPlan Called =====");
        return dayWiseSewingProductionRepository
                .findBySewingPlanIdOrderByDateAsc(
                        sewingPlanId)
                .stream()
                .map(
                        DayWiseSewingProductionMapper::toDto)
                .toList();
    }

    @Override
    public SewingPlanProgressResponseDto getProgress(Long sewingPlanId, Long productionLineId) {

        SewingPlan sewingPlan =
                sewingPlanRepository.findById(
                                sewingPlanId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Sewing Plan Not Found"));


//          Find Line Target

        SewingTarget target =
                sewingPlan.getTargets()
                        .stream()
                        .filter(t ->
                                t.getProductionLine()
                                        .getId()
                                        .equals(
                                                productionLineId))
                        .findFirst()
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Target Not Found"));

        /*
         * Sum Achieved Qty
         */
        int achievedSoFar =
                dayWiseSewingProductionRepository
                        .findBySewingPlanIdAndProductionLineId(
                                sewingPlanId,
                                productionLineId)
                        .stream()
                        .mapToInt(
                                p -> p.getAchievedQuantity() == null
                                        ? 0
                                        : p.getAchievedQuantity())
                        .sum();

        int remaining =
                target.getTargetQuantity()
                        - achievedSoFar;

        if (remaining < 0) {
            remaining = 0;
        }

        return new SewingPlanProgressResponseDto(
                sewingPlan.getInputReceivedQty(),
                target.getTargetQuantity(),
                achievedSoFar,
                remaining );

    }

//    Auto Update Sewing Plan  Output Qty,Rejection Qty,Status


    private void updateSewingPlanProgress(
            SewingPlan sewingPlan) {

        List<DayWiseSewingProduction> productions =
                dayWiseSewingProductionRepository
                        .findBySewingPlanId(
                                sewingPlan.getId());

        int totalOutput =
                productions.stream()
                        .mapToInt(
                                p -> p.getAchievedQuantity() == null
                                        ? 0
                                        : p.getAchievedQuantity())
                        .sum();

        int totalReject =
                productions.stream()
                        .mapToInt(
                                p -> p.getRejectionQty() == null
                                        ? 0
                                        : p.getRejectionQty())
                        .sum();

        sewingPlan.setOutputQty(
                totalOutput);

        sewingPlan.setRejectionQty(
                totalReject);

//         Auto Complete Sewing Plan  Output Qty reaches Input Received Qty

        // =====================================
// Auto Update Sewing Plan Status
// =====================================

        if (totalOutput == 0) {

            sewingPlan.setStatus(
                    SewingPlanStatus.PENDING);

        }
        else if (totalOutput < sewingPlan.getInputReceivedQty()) {

            sewingPlan.setStatus(
                    SewingPlanStatus.IN_SEWING);

        }
        else {

            sewingPlan.setStatus(
                    SewingPlanStatus.COMPLETED);

        }

        sewingPlanRepository.save(
                sewingPlan);

    }
}
