package com.emranhss.GarmentsManagementSystem.service;

import com.emranhss.GarmentsManagementSystem.dto.request.ItemRequestDto;
import com.emranhss.GarmentsManagementSystem.dto.response.ItemResponseDto;

import java.util.List;

public interface ItemService {
    ItemResponseDto create(ItemRequestDto request);

    ItemResponseDto update(Long id,
                           ItemRequestDto request);

    ItemResponseDto getById(Long id);

    List<ItemResponseDto> getAll();

    void delete(Long id);
}
