package com.emranhss.GarmentsManagementSystem.serviceimp;

import com.emranhss.GarmentsManagementSystem.dto.mapper.PackingPlanMapper;
import com.emranhss.GarmentsManagementSystem.dto.mapper.ShipmentMapper;
import com.emranhss.GarmentsManagementSystem.dto.request.ShipmentRequestDto;
import com.emranhss.GarmentsManagementSystem.dto.response.PackingPlanResponseDto;
import com.emranhss.GarmentsManagementSystem.dto.response.ShipmentResponseDto;
import com.emranhss.GarmentsManagementSystem.entity.PackingPlan;
import com.emranhss.GarmentsManagementSystem.entity.Shipment;
import com.emranhss.GarmentsManagementSystem.enums.PackingPlanStatus;
import com.emranhss.GarmentsManagementSystem.enums.ShipmentStatus;
import com.emranhss.GarmentsManagementSystem.repository.PackingPlanRepository;
import com.emranhss.GarmentsManagementSystem.repository.ShipmentRepository;
import com.emranhss.GarmentsManagementSystem.service.ShipmentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ShipmentServiceImpl implements ShipmentService {

    private final ShipmentRepository shipmentRepository;

    private final PackingPlanRepository packingPlanRepository;


    @Override
    public ShipmentResponseDto create(ShipmentRequestDto requestDto) {
        // ==========================
        // Load Packing Plan
        // ==========================

        PackingPlan packingPlan =
                packingPlanRepository.findById(
                                requestDto.getPackingPlanId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Packing Plan Not Found"));

        // ==========================
        // Business Rule
        // Only READY_TO_SHIP Packing Plan
        // ==========================

        if (packingPlan.getStatus()
                != PackingPlanStatus.READY_TO_SHIP) {

            throw new RuntimeException(
                    "Packing Plan is not ready for shipment");
        }

        // ==========================
        // One Packing Plan
        // One Shipment
        // ==========================

        if (shipmentRepository.existsByPackingPlanId(
                packingPlan.getId())) {

            throw new RuntimeException(
                    "Shipment already created for this Packing Plan");
        }

        // ==========================
        // Create Shipment
        // ==========================

        Shipment shipment = new Shipment();

        // Auto Generate Shipment No

        shipment.setShipmentNo(
                "SHP-" + System.currentTimeMillis());

        shipment.setPackingPlan(
                packingPlan);

        // ==========================
        // Auto Fill
        // ==========================

        shipment.setBuyerName(
                packingPlan.getBuyerName());

        shipment.setDestination(
                packingPlan.getOrder().getShippingAddress());

        shipment.setOrderNo(
                packingPlan.getOrderNo());

        shipment.setStyleNo(
                packingPlan.getStyleNo());

        shipment.setShipmentQty(
                packingPlan.getTotalPackedQty());

        // ==========================
        // User Input
        // ==========================

        shipment.setShipmentDate(
                requestDto.getShipmentDate());

        shipment.setRemarks(
                requestDto.getRemarks());

        // ==========================
        // Initial Status
        // ==========================

        shipment.setStatus(
                ShipmentStatus.PENDING);

        Shipment saved =
                shipmentRepository.save(
                        shipment);

        return ShipmentMapper.toDto(
                saved);

    }

    @Override
    public ShipmentResponseDto update(Long id, ShipmentRequestDto requestDto) {
        Shipment shipment =
                shipmentRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Shipment Not Found"));

        // Only User Editable Fields

        shipment.setShipmentDate(
                requestDto.getShipmentDate());

        shipment.setRemarks(
                requestDto.getRemarks());

        Shipment updated =
                shipmentRepository.save(
                        shipment);

        return ShipmentMapper.toDto(
                updated);
    }

    @Override
    public ShipmentResponseDto getById(Long id) {
        Shipment shipment =
                shipmentRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Shipment Not Found"));

        return ShipmentMapper.toDto(
                shipment);
    }

    @Override
    public List<ShipmentResponseDto> getAll() {
        return shipmentRepository
                .findAllByOrderByShipmentDateDesc()
                .stream()
                .map(ShipmentMapper::toDto)
                .toList();
    }

    @Override
    public void delete(Long id) {

        Shipment shipment =
                shipmentRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Shipment Not Found"));

        // Already Shipped Shipment Delete Not Allowed

        if (shipment.getStatus()
                == ShipmentStatus.SHIPPED) {

            throw new RuntimeException(
                    "Shipped Shipment can not be deleted");
        }

        shipmentRepository.delete(
                shipment);


    }

    @Override
    public List<PackingPlanResponseDto> getAvailablePackingPlans() {
        return packingPlanRepository
                .findByStatus(PackingPlanStatus.READY_TO_SHIP)
                .stream()
                .filter(plan ->
                        !shipmentRepository.existsByPackingPlanId(plan.getId()))
                .map(PackingPlanMapper::toDto)
                .toList();
    }

    @Override
    public ShipmentResponseDto markAsShipped(Long id) {
        Shipment shipment =
                shipmentRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Shipment Not Found"));

        if (shipment.getStatus()
                == ShipmentStatus.SHIPPED) {

            throw new RuntimeException(
                    "Shipment already shipped");
        }

        shipment.setStatus(
                ShipmentStatus.SHIPPED);

        Shipment updated =
                shipmentRepository.save(
                        shipment);

        return ShipmentMapper.toDto(
                updated);

    }




}
