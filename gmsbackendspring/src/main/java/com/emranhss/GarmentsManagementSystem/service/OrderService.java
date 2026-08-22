package com.emranhss.GarmentsManagementSystem.service;

import com.emranhss.GarmentsManagementSystem.dto.request.OrderRequestDto;
import com.emranhss.GarmentsManagementSystem.dto.response.OrderResponseDto;

import java.util.List;

public interface OrderService {
    OrderResponseDto create(OrderRequestDto request);

    OrderResponseDto update(Long id,
                            OrderRequestDto request);

    OrderResponseDto getById(Long id);

    List<OrderResponseDto> getAll();

    void delete(Long id);

    List<OrderResponseDto> getByBuyer(Long buyerId);

    List<OrderResponseDto> getAvailableOrdersByBuyer(Long buyerId);

    List<OrderResponseDto> getAvailableForRawMaterialCheck();

    List<OrderResponseDto> getAvailableForFabricCheck();
}
