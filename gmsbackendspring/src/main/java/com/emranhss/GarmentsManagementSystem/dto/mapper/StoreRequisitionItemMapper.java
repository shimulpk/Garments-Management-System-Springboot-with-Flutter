package com.emranhss.GarmentsManagementSystem.dto.mapper;

import com.emranhss.GarmentsManagementSystem.dto.response.StoreRequisitionItemResponseDto;
import com.emranhss.GarmentsManagementSystem.entity.StoreRequisitionItem;

public class StoreRequisitionItemMapper {

    public static StoreRequisitionItemResponseDto toDto(
            StoreRequisitionItem item) {

        if (item == null) {
            return null;
        }

        StoreRequisitionItemResponseDto dto =
                new StoreRequisitionItemResponseDto();

        dto.setId(item.getId());

        dto.setItemId(item.getItem().getId());

        dto.setItemName(item.getItem().getItemName());

        dto.setUnit(item.getItem().getUnit());

        dto.setQuantity(item.getQuantity());



        return dto;
    }

}
