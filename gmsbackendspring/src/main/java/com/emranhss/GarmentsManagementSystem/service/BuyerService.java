package com.emranhss.GarmentsManagementSystem.service;

import com.emranhss.GarmentsManagementSystem.dto.request.BuyerRequestDto;
import com.emranhss.GarmentsManagementSystem.dto.response.BuyerResponseDto;
import com.emranhss.GarmentsManagementSystem.entity.Buyer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface BuyerService {
    BuyerResponseDto create(BuyerRequestDto request);

    BuyerResponseDto update(Long id,
                            BuyerRequestDto request);

    BuyerResponseDto getById(Long id);

    List<BuyerResponseDto> getAll();

    void delete(Long id);
}
