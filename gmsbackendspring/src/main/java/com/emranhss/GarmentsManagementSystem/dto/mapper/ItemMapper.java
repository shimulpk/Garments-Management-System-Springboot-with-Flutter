package com.emranhss.GarmentsManagementSystem.dto.mapper;

import com.emranhss.GarmentsManagementSystem.dto.request.ItemRequestDto;
import com.emranhss.GarmentsManagementSystem.dto.response.ItemResponseDto;
import com.emranhss.GarmentsManagementSystem.entity.Item;

public class ItemMapper {
    public static Item toEntity(
            ItemRequestDto dto) {

        if (dto == null) {
            return null;
        }

        Item item = new Item();

        item.setItemName(dto.getItemName());
        item.setCategory(dto.getCategory());
        item.setUnit(dto.getUnit());

        return item;
    }

    public static ItemResponseDto toDto(
            Item item) {

        if (item == null) {
            return null;
        }

        ItemResponseDto dto =
                new ItemResponseDto();

        dto.setId(item.getId());
        dto.setItemName(item.getItemName());
        dto.setCategory(item.getCategory());
        dto.setUnit(item.getUnit());
        dto.setActive(item.getActive());

        return dto;
    }
}
