package com.emranhss.GarmentsManagementSystem.service;

import com.emranhss.GarmentsManagementSystem.dto.request.BomViewRequestDto;
import com.emranhss.GarmentsManagementSystem.dto.response.BomViewResponseDto;

import java.util.List;

public interface BomViewService {
    BomViewResponseDto create(BomViewRequestDto request);

    BomViewResponseDto update(Long id,
                              BomViewRequestDto request);

    BomViewResponseDto getById(Long id);

    List<BomViewResponseDto> getAll();

    List<BomViewResponseDto> getByStyle(Long styleId);

    void delete(Long id);
}
