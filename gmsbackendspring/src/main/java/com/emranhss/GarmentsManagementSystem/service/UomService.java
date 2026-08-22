package com.emranhss.GarmentsManagementSystem.service;

import com.emranhss.GarmentsManagementSystem.dto.request.UomRequestDto;
import com.emranhss.GarmentsManagementSystem.dto.response.UomResponseDto;

import java.util.List;

public interface UomService {
    UomResponseDto create(UomRequestDto request);

    UomResponseDto update(Long id,
                          UomRequestDto request);

    UomResponseDto getById(Long id);

    List<UomResponseDto> getAll();

    void delete(Long id);
}
