package com.emranhss.GarmentsManagementSystem.service;

import com.emranhss.GarmentsManagementSystem.dto.response.StockResponseDto;

import java.util.List;

public interface StockService {

    List<StockResponseDto> getAll();

    StockResponseDto getById(Long id);

    List<StockResponseDto> getAvailableStocks();
}
