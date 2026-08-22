package com.emranhss.GarmentsManagementSystem.dto.mapper;

import com.emranhss.GarmentsManagementSystem.dto.request.UomRequestDto;
import com.emranhss.GarmentsManagementSystem.dto.response.UomResponseDto;
import com.emranhss.GarmentsManagementSystem.entity.Uom;

public class UomMapper {
    public static Uom toEntity(UomRequestDto dto) {

        if (dto == null) {
            return null;
        }

        Uom uom = new Uom();

        uom.setProductName(dto.getProductName());
        uom.setSize(dto.getSize());
        uom.setBody(dto.getBody());
        uom.setSleeve(dto.getSleeve());
        uom.setPocket(dto.getPocket());
        uom.setWastage(dto.getWastage());
        uom.setShrinkage(dto.getShrinkage());

        return uom;
    }

    public static UomResponseDto toDto(Uom uom) {

        if (uom == null) {
            return null;
        }

        UomResponseDto dto =
                new UomResponseDto();

        dto.setId(uom.getId());
        dto.setProductName(uom.getProductName());
        dto.setSize(uom.getSize());
        dto.setBody(uom.getBody());
        dto.setSleeve(uom.getSleeve());
        dto.setPocket(uom.getPocket());
        dto.setWastage(uom.getWastage());
        dto.setShrinkage(uom.getShrinkage());
        dto.setTotalBaseFabric(uom.getTotalBaseFabric());

        return dto;
    }
}
