package com.emranhss.GarmentsManagementSystem.dto.mapper;

import com.emranhss.GarmentsManagementSystem.dto.response.MaterialIssueItemResponseDto;
import com.emranhss.GarmentsManagementSystem.entity.MaterialIssueItem;

public class MaterialIssueItemMapper {

    public static MaterialIssueItemResponseDto toDto(MaterialIssueItem entity) {

        if (entity == null) {
            return null;
        }

        MaterialIssueItemResponseDto dto = new MaterialIssueItemResponseDto();

        dto.setId(entity.getId());

        dto.setItemId(entity.getItem().getId());

        dto.setItemName(entity.getItem().getItemName());

        dto.setUnit(entity.getItem().getUnit());

        dto.setQuantity(entity.getQuantity());

        return dto;

    }

}
