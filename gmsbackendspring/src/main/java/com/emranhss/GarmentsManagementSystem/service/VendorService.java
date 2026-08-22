package com.emranhss.GarmentsManagementSystem.service;

import com.emranhss.GarmentsManagementSystem.dto.request.VendorRequestDto;
import com.emranhss.GarmentsManagementSystem.dto.response.VendorResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface VendorService {
    VendorResponseDto create(
            VendorRequestDto request);

    VendorResponseDto update(
            Long id,
            VendorRequestDto request);

    VendorResponseDto getById(
            Long id);

    List<VendorResponseDto> getAll();

    void delete(Long id);
}
