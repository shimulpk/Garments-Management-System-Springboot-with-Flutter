package com.emranhss.GarmentsManagementSystem.dto.mapper;

import com.emranhss.GarmentsManagementSystem.dto.request.BomStyleRequestDto;
import com.emranhss.GarmentsManagementSystem.dto.response.BomStyleResponseDto;
import com.emranhss.GarmentsManagementSystem.entity.BomStyle;

public class BomStyleMapper {
    public static BomStyle toEntity(BomStyleRequestDto dto) {

        if (dto == null) {
            return null;
        }

        BomStyle style = new BomStyle();

        style.setStyleCode(dto.getStyleCode());
        style.setStyleName(dto.getStyleName());
        style.setStyleType(dto.getStyleType());
        style.setDescription(dto.getDescription());
        style.setApprovalStatus(dto.getApprovalStatus());
        style.setSizeSet(dto.getSizeSet());

        return style;
    }

    public static BomStyleResponseDto toDto(BomStyle style) {

        if (style == null) {
            return null;
        }

        BomStyleResponseDto dto = new BomStyleResponseDto();

        dto.setId(style.getId());

        // Buyer new add
        if (style.getBuyer() != null) {

            dto.setBuyerId(style.getBuyer().getId());
            dto.setBuyerCode(style.getBuyer().getBuyerCode());
            dto.setBuyerName(style.getBuyer().getBuyerName());

        }


        dto.setStyleCode(style.getStyleCode());
        dto.setStyleName(style.getStyleName());
        dto.setStyleType(style.getStyleType());
        dto.setDescription(style.getDescription());
        dto.setApprovalStatus(style.getApprovalStatus());
        dto.setSizeSet(style.getSizeSet());
        dto.setActive(style.getActive());

        return dto;
    }
}
