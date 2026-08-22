package com.emranhss.GarmentsManagementSystem.service;

import com.emranhss.GarmentsManagementSystem.dto.request.FabricsCheckRequestDto;
import com.emranhss.GarmentsManagementSystem.dto.response.FabricsCheckResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FabricsCheckService {
    FabricsCheckResponseDto generate(
            FabricsCheckRequestDto request);

    FabricsCheckResponseDto getById(Long id);

    List<FabricsCheckResponseDto> getAll();

    void delete(Long id);

    FabricsCheckResponseDto getByOrder(Long orderId);
}
