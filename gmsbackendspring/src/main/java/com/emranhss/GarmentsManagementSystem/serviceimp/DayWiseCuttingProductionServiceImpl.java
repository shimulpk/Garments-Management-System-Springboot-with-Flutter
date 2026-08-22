package com.emranhss.GarmentsManagementSystem.serviceimp;

import com.emranhss.GarmentsManagementSystem.dto.mapper.DayWiseCuttingProductionMapper;
import com.emranhss.GarmentsManagementSystem.dto.request.DayWiseCuttingProductionRequestDto;
import com.emranhss.GarmentsManagementSystem.dto.request.DayWiseCuttingProductionUpdateRequestDto;
import com.emranhss.GarmentsManagementSystem.dto.response.*;
import com.emranhss.GarmentsManagementSystem.entity.CuttingPlan;
import com.emranhss.GarmentsManagementSystem.entity.DayWiseCuttingProduction;

import com.emranhss.GarmentsManagementSystem.enums.CuttingPlanStatus;

import com.emranhss.GarmentsManagementSystem.repository.CuttingPlanRepository;
import com.emranhss.GarmentsManagementSystem.repository.DayWiseCuttingProductionRepository;
import com.emranhss.GarmentsManagementSystem.repository.OrderRepository;
import com.emranhss.GarmentsManagementSystem.service.DayWiseCuttingProductionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DayWiseCuttingProductionServiceImpl implements DayWiseCuttingProductionService {

    private final DayWiseCuttingProductionRepository productionRepository;
    private final CuttingPlanRepository cuttingPlanRepository;
    private final OrderRepository orderRepository;


    @Override
    public DayWiseCuttingProductionResponseDto create(DayWiseCuttingProductionRequestDto request) {



        CuttingPlan plan = cuttingPlanRepository.findById(request.getCuttingPlanId())
                .orElseThrow(() -> new RuntimeException("Cutting Plan Not Found"));

        // Ager Cutting Check
        Integer currentTotalCut = productionRepository
                .findByCuttingPlanId(plan.getId())
                .stream()
                .mapToInt(DayWiseCuttingProduction::getActualCutPieces)
                .sum();

        // Notun Input Dile Check
        int newTotalCut = currentTotalCut + request.getActualCutPieces();

        // OverTarget Not Saved
        if (newTotalCut > plan.getPlannedPieces()) {
            throw new RuntimeException("Cut quantity exceeds planned target! Planned: "
                    + plan.getPlannedPieces() + ", Current Total: " + currentTotalCut);
        }


        DayWiseCuttingProduction production = DayWiseCuttingProductionMapper.toEntity(request);
        production.setCuttingPlan(plan);
        production.setStyleNo(plan.getStyleNo());
        production.setCuttingMaster(plan.getCuttingMaster());
        production.setCreatedAt(LocalDateTime.now());

        DayWiseCuttingProduction saved = productionRepository.save(production);

        // Change Status
        if (newTotalCut == plan.getPlannedPieces()) {
            plan.setStatus(CuttingPlanStatus.COMPLETED);
        } else {
            plan.setStatus(CuttingPlanStatus.PENDING);
        }

        cuttingPlanRepository.save(plan);

        return DayWiseCuttingProductionMapper.toDto(saved);
    }

    @Override
    public DayWiseCuttingProductionResponseDto getById(Long id) {
        DayWiseCuttingProduction dayWiseCuttingProduction = productionRepository.findById(id)
                .orElseThrow(()->
                new RuntimeException("CuttingProduction Not found"));
        return DayWiseCuttingProductionMapper.toDto(dayWiseCuttingProduction);
    }

    @Override
    public List<DayWiseCuttingProductionResponseDto> getAll() {
        return productionRepository.findAll()
                .stream()
                .map(DayWiseCuttingProductionMapper::toDto)
                .toList();
    }

    @Override
    public void delete(Long id) {
        productionRepository.deleteById(id);
    }

    @Override
    public CuttingPlanProgressResponseDto getProgress(Long cuttingPlanId) {
        CuttingPlan plan =
                cuttingPlanRepository.findById(cuttingPlanId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Cutting Plan Not Found"));

        Integer cutSoFar =
                productionRepository
                        .findByCuttingPlanId(cuttingPlanId)
                        .stream()
                        .mapToInt(
                                DayWiseCuttingProduction::getActualCutPieces)
                        .sum();

        Integer target =
                plan.getPlannedPieces();

        Integer remaining =
                target - cutSoFar;

        Double progress =
                target == 0
                        ? 0.0
                        : (cutSoFar * 100.0) / target;

        Integer rejected =
                productionRepository
                        .getTotalReject(cuttingPlanId);

        CuttingPlanProgressResponseDto responseDto = new CuttingPlanProgressResponseDto();

        responseDto.setCutSoFar(cutSoFar);
        responseDto.setTarget(target);
        responseDto.setRemaining(remaining);
        responseDto.setProgress(progress);
        responseDto.setRejected(rejected);
        responseDto.setStatus(plan.getStatus());
        return responseDto;
    }

    @Override
    public List<DayWiseCuttingProductionResponseDto> getByCuttingPlan(Long cuttingPlanId) {
        return productionRepository
                .findByCuttingPlanIdOrderByDateAsc(cuttingPlanId)
                .stream()
                .map(DayWiseCuttingProductionMapper::toDto)
                .toList();

    }
// for android use
    @Override
    public List<DayWiseCuttingHistoryResponseDto> getHistory() {
        return productionRepository.getHistory();
    }
    // for android use
    @Override
    public List<DayWiseCuttingHistoryResponseDto> getHistory(LocalDate date) {
        return productionRepository.getHistoryByDate(date);
    }
    // for android use
    @Override
    public List<DayWiseCuttingHistoryDetailsResponseDto> getHistoryDetails(Long cuttingPlanId, LocalDate date) {
        return productionRepository.getHistoryDetails(
                cuttingPlanId,
                date
        );
    }

    @Override
    @Transactional
    public DayWiseCuttingProductionResponseDto update(Long id, DayWiseCuttingProductionUpdateRequestDto requestDto) {
        DayWiseCuttingProduction production = productionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Production entry not found"));

        CuttingPlan plan = production.getCuttingPlan();


        List<DayWiseCuttingProduction> allProductions = productionRepository.findByCuttingPlanId(plan.getId());


        int otherTotalCut = allProductions.stream()
                .filter(p -> !p.getId().equals(id))
                .mapToInt(DayWiseCuttingProduction::getActualCutPieces)
                .sum();


        int newTotalCut = otherTotalCut + requestDto.getActualCutPieces();


        if (newTotalCut > plan.getPlannedPieces()) {
            throw new RuntimeException("Cannot update! Total cut pieces (" + newTotalCut
                    + ") exceeds target planned pieces (" + plan.getPlannedPieces() + ")");
        }


        production.setActualCutPieces(requestDto.getActualCutPieces());
        production.setRejectPieces(requestDto.getRejectPieces());

        DayWiseCuttingProduction updatedProduction = productionRepository.save(production);

        
        if (newTotalCut >= plan.getPlannedPieces()) {
            plan.setStatus(CuttingPlanStatus.COMPLETED);
        } else {
            plan.setStatus(CuttingPlanStatus.PENDING);
        }

        cuttingPlanRepository.save(plan);

        return DayWiseCuttingProductionMapper.toDto(updatedProduction);
    }

    @Override
    public DayWiseCuttingHistorySummaryResponseDto getHistorySummary(Long cuttingPlanId, LocalDate date) {
        return productionRepository.getHistorySummary(
                cuttingPlanId,
                date
        );
    }
}
