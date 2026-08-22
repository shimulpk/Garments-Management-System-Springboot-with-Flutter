package com.emranhss.GarmentsManagementSystem.serviceimp;

import com.emranhss.GarmentsManagementSystem.dto.mapper.FinishingPlanMapper;
import com.emranhss.GarmentsManagementSystem.dto.mapper.PackingPlanMapper;
import com.emranhss.GarmentsManagementSystem.dto.request.PackingPlanRequestDto;
import com.emranhss.GarmentsManagementSystem.dto.response.FinishingPlanResponseDto;
import com.emranhss.GarmentsManagementSystem.dto.response.PackingPlanResponseDto;
import com.emranhss.GarmentsManagementSystem.entity.FinishingPlan;
import com.emranhss.GarmentsManagementSystem.entity.Order;
import com.emranhss.GarmentsManagementSystem.entity.PackingPlan;
import com.emranhss.GarmentsManagementSystem.enums.FinishingPlanStatus;
import com.emranhss.GarmentsManagementSystem.enums.PackingPlanStatus;
import com.emranhss.GarmentsManagementSystem.repository.FinishingPlanRepository;
import com.emranhss.GarmentsManagementSystem.repository.PackingPlanRepository;
import com.emranhss.GarmentsManagementSystem.service.PackingPlanService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PackingPlanServiceImpl implements PackingPlanService {

    private final PackingPlanRepository packingPlanRepository;
    private final FinishingPlanRepository finishingPlanRepository;




    @Override
    public PackingPlanResponseDto create(PackingPlanRequestDto request) {
//         Load Finishing Plan

        FinishingPlan finishingPlan =
                finishingPlanRepository.findById(
                                request.getFinishingPlanId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Finishing Plan Not Found"));

//       Auto Order Finishing - Sewing - Cutting -Order


        Order order =
                finishingPlan.getSewingPlan()
                        .getCuttingPlan()
                        .getOrder();

        PackingPlan packingPlan =
                new PackingPlan();

//         Auto Generate Packing Plan Id

        packingPlan.setPackingPlanId(
                "PKG-" + System.currentTimeMillis());

//         Relations

        packingPlan.setFinishingPlan(
                finishingPlan);

        packingPlan.setOrder(
                order);

//         Auto Fill

        packingPlan.setBuyerName(
                finishingPlan.getBuyerName());

        packingPlan.setOrderNo(
                order.getOrderId());

        packingPlan.setStyleNo(
                finishingPlan.getStyleNo());

        packingPlan.setColor(
                finishingPlan.getColor());

//         Auto Quantity

        packingPlan.setTotalOrderQty(
                order.getTotalQuantity());

        packingPlan.setInputQty(
                finishingPlan.getPassQty());

//         Initial Values

        packingPlan.setTotalPackedQty(0);
        packingPlan.setRejectionQty(0);

//        User Input

        packingPlan.setPackingMethod(
                request.getPackingMethod());

        packingPlan.setPcsPerCarton(
                request.getPcsPerCarton());

        /*
         * Auto Carton Calculation
         * Formula:
         * Total Planned Cartons =
         * ceil(Total Order Qty / Pcs Per Carton)
         */
        int totalCartons =
                (int) Math.ceil(
                        (double) order.getTotalQuantity()
                                / request.getPcsPerCarton());

        packingPlan.setTotalPlannedCartons(
                totalCartons);

        packingPlan.setPolyBagType(
                request.getPolyBagType());

        packingPlan.setHangTag(
                request.getHangTag());

        packingPlan.setPackingSupervisor(
                request.getPackingSupervisor());

        packingPlan.setStartDate(
                request.getStartDate());

        packingPlan.setExpectedShipmentDate(
                request.getExpectedShipmentDate());

//       Initial Status

        packingPlan.setStatus(
                PackingPlanStatus.PENDING);

        PackingPlan saved =
                packingPlanRepository.save(
                        packingPlan);

        return buildResponse(saved);
    }

    @Override
    public PackingPlanResponseDto update(Long id, PackingPlanRequestDto request) {
        PackingPlan packingPlan =
                packingPlanRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Packing Plan Not Found"));


        FinishingPlan finishingPlan =
                finishingPlanRepository.findById(
                                request.getFinishingPlanId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Finishing Plan Not Found"));

//         Auto Order

        Order order =
                finishingPlan.getSewingPlan()
                        .getCuttingPlan()
                        .getOrder();

        packingPlan.setFinishingPlan(
                finishingPlan);

        packingPlan.setOrder(
                order);

//         Auto Fill

        packingPlan.setBuyerName(
                finishingPlan.getBuyerName());

        packingPlan.setOrderNo(
                order.getOrderId());

        packingPlan.setStyleNo(
                finishingPlan.getStyleNo());

        packingPlan.setColor(
                finishingPlan.getColor());

        packingPlan.setTotalOrderQty(
                order.getTotalQuantity());

        packingPlan.setInputQty(
                finishingPlan.getPassQty());

//         User Input

        packingPlan.setPackingMethod(
                request.getPackingMethod());

        packingPlan.setPcsPerCarton(
                request.getPcsPerCarton());

//         Auto Carton Calculation

        int totalCartons =
                (int) Math.ceil(
                        (double) order.getTotalQuantity()
                                / request.getPcsPerCarton());

        packingPlan.setTotalPlannedCartons(
                totalCartons);

        packingPlan.setPolyBagType(
                request.getPolyBagType());

        packingPlan.setHangTag(
                request.getHangTag());

        packingPlan.setPackingSupervisor(
                request.getPackingSupervisor());

        packingPlan.setStartDate(
                request.getStartDate());

        packingPlan.setExpectedShipmentDate(
                request.getExpectedShipmentDate());

        PackingPlan updated =
                packingPlanRepository.save(
                        packingPlan);

        return buildResponse(updated);
    }

    @Override
    public PackingPlanResponseDto getById(Long id) {
        PackingPlan packingPlan =
                packingPlanRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Packing Plan Not Found"));

        return buildResponse(
                packingPlan);
    }

    @Override
    public List<PackingPlanResponseDto> getAll() {
        return packingPlanRepository.findAll()
                .stream()
                .map(this::buildResponse)
                .toList();
    }

    @Override
    public void delete(Long id) {
        PackingPlan packingPlan =
                packingPlanRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Packing Plan Not Found"));

        packingPlanRepository.delete(
                packingPlan);
    }

    @Override
    public List<FinishingPlanResponseDto> getAvailableFinishingPlans() {
        return finishingPlanRepository
                .findByStatus(FinishingPlanStatus.COMPLETED)
                .stream()
                .filter(plan ->
                        !packingPlanRepository.existsByFinishingPlanId(plan.getId()))
                .map(FinishingPlanMapper::toDto)
                .toList();
    }

    @Override
    public List<PackingPlanResponseDto> getAvailableForDayWisePacking() {
        return packingPlanRepository
                .findAll()
                .stream()
                .filter(plan ->
                        plan.getStatus() != PackingPlanStatus.READY_TO_SHIP)
                .map(PackingPlanMapper::toDto)
                .toList();
    }


    private PackingPlanResponseDto buildResponse(
            PackingPlan packingPlan) {

        PackingPlanResponseDto dto =
                PackingPlanMapper.toDto(
                        packingPlan);

        int input =
                packingPlan.getInputQty() == null
                        ? 0
                        : packingPlan.getInputQty();

        int packed =
                packingPlan.getTotalPackedQty() == null
                        ? 0
                        : packingPlan.getTotalPackedQty();

        dto.setPackedSoFar(
                packed);

        dto.setRemaining(
                Math.max(
                        0,
                        input - packed));

        double progress =
                input == 0
                        ? 0
                        : (packed * 100.0) / input;

        dto.setProgressPercentage(
                Math.round(progress * 100.0) / 100.0);

        return dto;

    }
}
