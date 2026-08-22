package com.emranhss.GarmentsManagementSystem.service;

import com.emranhss.GarmentsManagementSystem.dto.request.StoreRequisitionRequestDto;
import com.emranhss.GarmentsManagementSystem.dto.response.StoreRequisitionResponseDto;

import java.util.List;

public interface StoreRequisitionService {

    StoreRequisitionResponseDto create(
            StoreRequisitionRequestDto request);

    StoreRequisitionResponseDto update(
            Long id,
            StoreRequisitionRequestDto request);

    StoreRequisitionResponseDto getById(Long id);

    List<StoreRequisitionResponseDto> getAll();

    void delete(Long id);

    List<StoreRequisitionResponseDto> getPendingRequisitions();

    StoreRequisitionResponseDto approve(Long id);

    StoreRequisitionResponseDto reject(Long id);

    List<StoreRequisitionResponseDto> getApprovedRequisitions();
}
