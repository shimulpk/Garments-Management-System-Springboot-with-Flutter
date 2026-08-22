package com.emranhss.GarmentsManagementSystem.serviceimp;

import com.emranhss.GarmentsManagementSystem.dto.mapper.DayWisePackingProductionMapper;
import com.emranhss.GarmentsManagementSystem.dto.request.DayWisePackingProductionRequestDto;
import com.emranhss.GarmentsManagementSystem.dto.response.DayWisePackingProductionResponseDto;
import com.emranhss.GarmentsManagementSystem.dto.response.PackingPlanProgressResponseDto;
import com.emranhss.GarmentsManagementSystem.dto.response.PackingProductionSummaryResponseDto;
import com.emranhss.GarmentsManagementSystem.entity.DayWisePackingProduction;
import com.emranhss.GarmentsManagementSystem.entity.Order;
import com.emranhss.GarmentsManagementSystem.entity.PackingPlan;
import com.emranhss.GarmentsManagementSystem.enums.PackingPlanStatus;
import com.emranhss.GarmentsManagementSystem.enums.ProductionStage;
import com.emranhss.GarmentsManagementSystem.repository.DayWisePackingProductionRepository;
import com.emranhss.GarmentsManagementSystem.repository.OrderRepository;
import com.emranhss.GarmentsManagementSystem.repository.PackingPlanRepository;
import com.emranhss.GarmentsManagementSystem.service.DayWisePackingProductionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DayWisePackingProductionServiceImpl implements DayWisePackingProductionService {
    private final DayWisePackingProductionRepository dayWisePackingProductionRepository;

    private final PackingPlanRepository packingPlanRepository;

    private final OrderRepository orderRepository;

    @Override
    public DayWisePackingProductionResponseDto create(DayWisePackingProductionRequestDto request) {
// Load Packing Plan
        PackingPlan packingPlan = packingPlanRepository.findById(request.getPackingPlanId())
                .orElseThrow(() -> new RuntimeException("Packing Plan Not Found"));

        // =====================================
        // Target Validation Check for Create
        // =====================================
        int currentTotalPacked = dayWisePackingProductionRepository
                .findByPackingPlanIdOrderByDateAsc(packingPlan.getId())
                .stream()
                .mapToInt(p -> p.getTodayPackedQty() == null ? 0 : p.getTodayPackedQty())
                .sum();

        int inputQty = request.getTodayPackedQty() == null ? 0 : request.getTodayPackedQty();
        int proposedTotalPacked = currentTotalPacked + inputQty;

        if (proposedTotalPacked > packingPlan.getInputQty()) {
            throw new RuntimeException("Packed quantity exceeds packing plan target! Target Input Qty: "
                    + packingPlan.getInputQty() + ", Current Total Packed: " + currentTotalPacked);
        }

        DayWisePackingProduction production = new DayWisePackingProduction();
        production.setPackingPlan(packingPlan);
        production.setDate(request.getDate());
        production.setTodayPackedQty(request.getTodayPackedQty());
        production.setTodayRejectQty(request.getTodayRejectQty());

        // Auto Display Fields
        production.setBuyerName(packingPlan.getBuyerName());
        production.setOrderNo(packingPlan.getOrderNo());
        production.setStyleNo(packingPlan.getStyleNo());

        // Auto Carton Calculation ceil(Today Packed Qty / Pcs Per Carton)
        int cartons = (int) Math.ceil((double) request.getTodayPackedQty() / packingPlan.getPcsPerCarton());
        production.setTodayPackedCartons(cartons);

        production.setCreatedAt(java.time.LocalDateTime.now());

        DayWisePackingProduction saved = dayWisePackingProductionRepository.save(production);

        // Update Packing Plan
        recalculateAndSavePackingPlan(packingPlan);

        return DayWisePackingProductionMapper.toDto(saved);
    }


    @Override
    public DayWisePackingProductionResponseDto update(Long id, DayWisePackingProductionRequestDto request) {
        DayWisePackingProduction production = dayWisePackingProductionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Packing Production Not Found"));

        PackingPlan packingPlan = packingPlanRepository.findById(request.getPackingPlanId())
                .orElseThrow(() -> new RuntimeException("Packing Plan Not Found"));


        // Target Validation Check for Update
        
        int otherTotalPacked = dayWisePackingProductionRepository
                .findByPackingPlanIdOrderByDateAsc(packingPlan.getId())
                .stream()
                .filter(p -> !p.getId().equals(id)) // বর্তমান আপডেট হওয়া আইডি বাদ দেওয়া
                .mapToInt(p -> p.getTodayPackedQty() == null ? 0 : p.getTodayPackedQty())
                .sum();

        int inputQty = request.getTodayPackedQty() == null ? 0 : request.getTodayPackedQty();
        int proposedTotalPacked = otherTotalPacked + inputQty;

        if (proposedTotalPacked > packingPlan.getInputQty()) {
            throw new RuntimeException("Update failed! Packed quantity exceeds packing plan target! Target Input Qty: "
                    + packingPlan.getInputQty() + ", Other Entries Total Packed: " + otherTotalPacked);
        }

        production.setPackingPlan(packingPlan);
        production.setDate(request.getDate());
        production.setTodayPackedQty(request.getTodayPackedQty());
        production.setTodayRejectQty(request.getTodayRejectQty());

        production.setBuyerName(packingPlan.getBuyerName());
        production.setOrderNo(packingPlan.getOrderNo());
        production.setStyleNo(packingPlan.getStyleNo());

        // Auto Carton Calculation
        int cartons = (int) Math.ceil((double) request.getTodayPackedQty() / packingPlan.getPcsPerCarton());
        production.setTodayPackedCartons(cartons);

        DayWisePackingProduction updated = dayWisePackingProductionRepository.save(production);

        // Recalculate Packing Plan
        recalculateAndSavePackingPlan(packingPlan);

        return DayWisePackingProductionMapper.toDto(updated);
    }


    @Override
    public DayWisePackingProductionResponseDto getById(Long id) {
        return DayWisePackingProductionMapper.toDto(
                dayWisePackingProductionRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Packing Production Not Found")));
    }

    @Override
    public List<DayWisePackingProductionResponseDto> getAll() {
        return dayWisePackingProductionRepository
                .findAll()
                .stream()
                .map(
                        DayWisePackingProductionMapper::toDto)
                .toList();
    }

    @Override
    public void delete(Long id) {

        DayWisePackingProduction production = dayWisePackingProductionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Packing Production Not Found"));

        // Delete korar age main plan ti variable e rakhlam
        PackingPlan packingPlan = production.getPackingPlan();


        dayWisePackingProductionRepository.delete(production);

        // main plan notun kore update
        recalculateAndSavePackingPlan(packingPlan);
    }

    @Override
    public PackingPlanProgressResponseDto getProgress(Long packingPlanId) {
        PackingPlan packingPlan =
                packingPlanRepository.findById(
                                packingPlanId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Packing Plan Not Found"));

        List<DayWisePackingProduction> productions =
                dayWisePackingProductionRepository
                        .findByPackingPlanIdOrderByDateAsc(packingPlanId);

        int packedSoFar =
                productions.stream()
                        .mapToInt(p ->
                                p.getTodayPackedQty() == null
                                        ? 0
                                        : p.getTodayPackedQty())
                        .sum();

        int remaining =
                packingPlan.getInputQty() - packedSoFar;

        if (remaining < 0) {
            remaining = 0;
        }

        return new PackingPlanProgressResponseDto(
                packingPlan.getInputQty(),

                packingPlan.getPcsPerCarton(),

                packedSoFar,

                remaining
        );
    }

    @Override
    public List<DayWisePackingProductionResponseDto> getByPackingPlan(Long packingPlanId) {
        return dayWisePackingProductionRepository
                .findByPackingPlanIdOrderByDateAsc(packingPlanId)
                .stream()
                .map(DayWisePackingProductionMapper::toDto)
                .toList();

    }

    @Override
    public List<PackingProductionSummaryResponseDto> getSummary() {
        List<PackingPlan> packingPlans =
                dayWisePackingProductionRepository
                        .getPackingPlansWithProduction();

        return packingPlans.stream()
                .map(plan -> {

                    int packedSoFar =
                            plan.getTotalPackedQty() == null
                                    ? 0
                                    : plan.getTotalPackedQty();

                    int targetQty =
                            plan.getInputQty() == null
                                    ? 0
                                    : plan.getInputQty();

                    int remaining =
                            targetQty - packedSoFar;

                    if (remaining < 0) {
                        remaining = 0;
                    }

                    return new PackingProductionSummaryResponseDto(

                            plan.getId(),

                            plan.getPackingPlanId(),

                            plan.getBuyerName(),

                            plan.getOrderNo(),

                            plan.getStyleNo(),

                            plan.getColor(),

                            targetQty,

                            packedSoFar,

                            remaining,

                            plan.getStatus().name()

                    );

                })
                .toList();
    }

    private void recalculateAndSavePackingPlan(PackingPlan packingPlan) {
        List<DayWisePackingProduction> productions =
                dayWisePackingProductionRepository
                        .findByPackingPlanIdOrderByDateAsc(
                                packingPlan.getId());

        int totalPacked = productions.stream()
                .mapToInt(p -> p.getTodayPackedQty() == null ? 0 : p.getTodayPackedQty())
                .sum();

        int totalReject = productions.stream()
                .mapToInt(p -> p.getTodayRejectQty() == null ? 0 : p.getTodayRejectQty())
                .sum();

        packingPlan.setTotalPackedQty(totalPacked);
        packingPlan.setRejectionQty(totalReject);


        if (totalPacked >= packingPlan.getTotalOrderQty()) {
            packingPlan.setStatus(PackingPlanStatus.READY_TO_SHIP);


        } else {

            packingPlan.setStatus(PackingPlanStatus.PENDING);
        }

        packingPlanRepository.save(packingPlan);
    }
}
