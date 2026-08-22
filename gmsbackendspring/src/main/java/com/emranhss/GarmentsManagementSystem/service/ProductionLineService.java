package com.emranhss.GarmentsManagementSystem.service;

import com.emranhss.GarmentsManagementSystem.dto.request.ProductionLineRequestDto;
import com.emranhss.GarmentsManagementSystem.dto.response.ProductionLineResponseDto;

import java.util.List;

public interface ProductionLineService {
    ProductionLineResponseDto create(ProductionLineRequestDto request);
    ProductionLineResponseDto update(Long id, ProductionLineRequestDto request);
    ProductionLineResponseDto getById(Long id);
    List<ProductionLineResponseDto> getAll();
    void delete(Long id);
}
