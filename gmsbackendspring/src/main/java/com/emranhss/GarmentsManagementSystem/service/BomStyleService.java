package com.emranhss.GarmentsManagementSystem.service;

import com.emranhss.GarmentsManagementSystem.dto.request.BomStyleRequestDto;
import com.emranhss.GarmentsManagementSystem.dto.response.BomStyleResponseDto;

import java.util.List;

public interface BomStyleService {
    BomStyleResponseDto create(BomStyleRequestDto request);

    BomStyleResponseDto update(Long id,
                               BomStyleRequestDto request);

    BomStyleResponseDto getById(Long id);

    List<BomStyleResponseDto> getAll();

    void delete(Long id);

    List<BomStyleResponseDto> getByBuyer(Long buyerId);
}
