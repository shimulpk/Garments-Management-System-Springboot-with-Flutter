package com.emranhss.GarmentsManagementSystem.serviceimp;

import com.emranhss.GarmentsManagementSystem.dto.mapper.CuttingPlanMapper;
import com.emranhss.GarmentsManagementSystem.dto.request.CuttingPlanRequestDto;
import com.emranhss.GarmentsManagementSystem.dto.response.CuttingPlanResponseDto;
import com.emranhss.GarmentsManagementSystem.entity.Buyer;
import com.emranhss.GarmentsManagementSystem.entity.CuttingPlan;
import com.emranhss.GarmentsManagementSystem.entity.Order;
import com.emranhss.GarmentsManagementSystem.entity.FabricsCheck;
import com.emranhss.GarmentsManagementSystem.enums.CuttingPlanStatus;
import com.emranhss.GarmentsManagementSystem.repository.*;
import com.emranhss.GarmentsManagementSystem.service.CuttingPlanService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CuttingPlanServiceImpl implements CuttingPlanService {
    private final CuttingPlanRepository cuttingPlanRepository;

    private final BuyerRepository buyerRepository;

    private final OrderRepository orderRepository;

    private final FabricsCheckRepository rawMaterialCheckRepository;

    private final DayWiseCuttingProductionRepository dayWiseCuttingProductionRepository;

    private final SewingPlanRepository sewingPlanRepository;

    @Override
    public CuttingPlanResponseDto create(CuttingPlanRequestDto request) {
        Buyer buyer =
                buyerRepository.findById(
                                request.getBuyerId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Buyer Not Found"));

        Order order =
                orderRepository.findById(
                                request.getOrderId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Order Not Found"));

        if (cuttingPlanRepository.existsByOrder_Id(
                order.getId())) {

            throw  new RuntimeException(
                    "Cutting Plan Already Exists");
        }

        FabricsCheck rmc =
                rawMaterialCheckRepository
                        .findByOrder_Id(order.getId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Raw Material Check Not Found"));

        CuttingPlan cuttingPlan =
                CuttingPlanMapper.toEntity(
                        request);

        cuttingPlan.setCuttingPlanId(
                "CP-" + System.currentTimeMillis());

        cuttingPlan.setBuyer(buyer);

        cuttingPlan.setOrder(order);

        // Auto From Order
        cuttingPlan.setStyleNo(
                order.getStyle().getStyleCode());

        cuttingPlan.setPlannedPieces(
                order.getTotalQuantity());

        // Auto From RMC
        cuttingPlan.setTotalFabricRequired(
                rmc.getTotalFabricRequired());

        // Default Status
        cuttingPlan.setStatus(
                CuttingPlanStatus.PENDING);

        CuttingPlan saved =
                cuttingPlanRepository.save(
                        cuttingPlan);

        return CuttingPlanMapper.toDto(saved);
    }

    @Override
    public CuttingPlanResponseDto update(Long id, CuttingPlanRequestDto request) {
        CuttingPlan cuttingPlan =
                cuttingPlanRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Cutting Plan Not Found"));

        Buyer buyer =
                buyerRepository.findById(
                                request.getBuyerId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Buyer Not Found"));

        Order order =
                orderRepository.findById(
                                request.getOrderId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Order Not Found"));

        FabricsCheck rmc =
                rawMaterialCheckRepository
                        .findByOrder_Id(order.getId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Raw Material Check Not Found"));

        cuttingPlan.setBuyer(buyer);

        cuttingPlan.setOrder(order);

        cuttingPlan.setFabricType(
                request.getFabricType());

        cuttingPlan.setColor(
                request.getColor());

        cuttingPlan.setMarkerLength(
                request.getMarkerLength());

        cuttingPlan.setMarkerWidth(
                request.getMarkerWidth());

        cuttingPlan.setNumberOfPlies(
                request.getNumberOfPlies());

        cuttingPlan.setMarkerEfficiency(
                request.getMarkerEfficiency());

        cuttingPlan.setCuttingTableNumber(
                request.getCuttingTableNumber());

        cuttingPlan.setCuttingMaster(
                request.getCuttingMaster());

        cuttingPlan.setStartDate(
                request.getStartDate());

        cuttingPlan.setEndDate(
                request.getEndDate());

        // Auto Refresh
        cuttingPlan.setStyleNo(
                order.getStyle().getStyleCode());

        cuttingPlan.setPlannedPieces(
                order.getTotalQuantity());

        cuttingPlan.setTotalFabricRequired(
                rmc.getTotalFabricRequired());

        return CuttingPlanMapper.toDto(
                cuttingPlanRepository.save(
                        cuttingPlan));
    }

    @Override
    public CuttingPlanResponseDto getById(Long id) {
        return convertWithProgress(
                cuttingPlanRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Cutting Plan Not Found")));
    }

    @Override
    public List<CuttingPlanResponseDto> getAll() {
        return cuttingPlanRepository.findAll()
                .stream()
                .map(this::convertWithProgress)
                .toList();
    }

    @Override
    public void delete(Long id) {
        cuttingPlanRepository.deleteById(id);
    }

    @Override
    public List<CuttingPlanResponseDto> getPendingPlans() {
        return cuttingPlanRepository
                .findByStatus(CuttingPlanStatus.PENDING)
                .stream()
                .map(CuttingPlanMapper::toDto)
                .toList();
    }

    @Override
    public List<CuttingPlanResponseDto> getCompletedPlans() {
        return cuttingPlanRepository
                .findByStatus(CuttingPlanStatus.COMPLETED)
                .stream()
                .map(CuttingPlanMapper::toDto)
                .toList();
    }

    @Override
    public List<CuttingPlanResponseDto> getAvailableForSewingPlan() {
        return cuttingPlanRepository
                .findByStatus(CuttingPlanStatus.COMPLETED)
                .stream()
                .filter(plan ->
                        !sewingPlanRepository.existsByCuttingPlanId(plan.getId()))
                .map(CuttingPlanMapper::toDto)
                .toList();

    }


    private CuttingPlanResponseDto convertWithProgress(CuttingPlan plan) {

        CuttingPlanResponseDto dto =
                CuttingPlanMapper.toDto(plan);

        Integer target =
                plan.getPlannedPieces();

        Integer actual =
                dayWiseCuttingProductionRepository
                        .getTotalActualCut(plan.getId());

        Integer reject =
                dayWiseCuttingProductionRepository
                        .getTotalReject(plan.getId());

        if (actual == null)
            actual = 0;

        if (reject == null)
            reject = 0;

        Integer remaining =
                Math.max(target - actual, 0);

        Double progress =
                target == 0
                        ? 0
                        : actual * 100.0 / target;

        dto.setActualCutPieces(actual);

        dto.setRejectedPieces(reject);

        dto.setRemainingPieces(remaining);

        dto.setProgress(progress);

        return dto;
    }
}
