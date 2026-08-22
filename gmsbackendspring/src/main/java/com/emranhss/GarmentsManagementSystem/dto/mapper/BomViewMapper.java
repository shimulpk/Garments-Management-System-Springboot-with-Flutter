package com.emranhss.GarmentsManagementSystem.dto.mapper;

import com.emranhss.GarmentsManagementSystem.dto.request.BomViewRequestDto;
import com.emranhss.GarmentsManagementSystem.dto.response.BomViewResponseDto;
import com.emranhss.GarmentsManagementSystem.entity.BomView;

public class BomViewMapper {
    public static BomView toEntity(BomViewRequestDto dto) {

        if (dto == null) {
            return null;
        }

        BomView bomView = new BomView();

        bomView.setSerial(dto.getSerial());
        bomView.setMaterialName(dto.getMaterialName());
        bomView.setUnit(dto.getUnit());
        bomView.setBaseFabric(dto.getBaseFabric());
        bomView.setQuantity(dto.getQuantity());
        bomView.setUnitPrice(dto.getUnitPrice());

        return bomView;
    }

    public static BomViewResponseDto toDto(BomView bomView) {

        if (bomView == null) {
            return null;
        }

        BomViewResponseDto dto =
                new BomViewResponseDto();

        dto.setId(bomView.getId());
        dto.setSerial(bomView.getSerial());
        dto.setMaterialName(bomView.getMaterialName());
        dto.setUnit(bomView.getUnit());
        dto.setBaseFabric(bomView.getBaseFabric());

        dto.setQuantity(bomView.getQuantity());
        dto.setUnitPrice(bomView.getUnitPrice());
        dto.setTotalCost(bomView.getTotalCost());

        if (bomView.getStyle() != null) {

            dto.setStyleId(
                    bomView.getStyle().getId());

            dto.setStyleCode(
                    bomView.getStyle().getStyleCode());

            dto.setStyleName(
                    bomView.getStyle().getStyleName());
        }

        return dto;
    }
}
