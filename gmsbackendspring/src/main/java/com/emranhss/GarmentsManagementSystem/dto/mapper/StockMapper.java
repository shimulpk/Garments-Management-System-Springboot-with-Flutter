package com.emranhss.GarmentsManagementSystem.dto.mapper;

import com.emranhss.GarmentsManagementSystem.dto.response.StockResponseDto;
import com.emranhss.GarmentsManagementSystem.entity.Stock;

public class StockMapper {

    private StockMapper() {
    }

    public static StockResponseDto toDto(Stock entity) {

        StockResponseDto dto = new StockResponseDto();

        dto.setId(entity.getId());

        dto.setItemId(
                entity.getItem().getId());

        dto.setItemName(
                entity.getItem().getItemName());

        dto.setCategory(
                entity.getItem().getCategory());

        dto.setUnit(
                entity.getItem().getUnit());

        dto.setAvailableQuantity(
                entity.getAvailableQuantity());

        return dto;

    }
}
