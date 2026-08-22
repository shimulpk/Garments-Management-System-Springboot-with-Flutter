package com.emranhss.GarmentsManagementSystem.service;

import com.emranhss.GarmentsManagementSystem.dto.request.ShipmentRequestDto;
import com.emranhss.GarmentsManagementSystem.dto.response.PackingPlanResponseDto;
import com.emranhss.GarmentsManagementSystem.dto.response.ShipmentResponseDto;

import java.util.List;

public interface ShipmentService {

    ShipmentResponseDto create(ShipmentRequestDto requestDto);

    ShipmentResponseDto update(Long id, ShipmentRequestDto requestDto);

    ShipmentResponseDto getById(Long id);

    List<ShipmentResponseDto> getAll();

    void delete(Long id);

    List<PackingPlanResponseDto> getAvailablePackingPlans();

    ShipmentResponseDto markAsShipped(Long id);
}
