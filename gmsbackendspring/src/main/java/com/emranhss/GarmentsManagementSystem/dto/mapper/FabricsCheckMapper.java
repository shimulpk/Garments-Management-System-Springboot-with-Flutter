package com.emranhss.GarmentsManagementSystem.dto.mapper;

import com.emranhss.GarmentsManagementSystem.dto.response.FabricsCheckResponseDto;
import com.emranhss.GarmentsManagementSystem.dto.response.FabricDetailsResponseDto;
import com.emranhss.GarmentsManagementSystem.entity.FabricsCheck;
import com.emranhss.GarmentsManagementSystem.entity.FabricDetails;

import java.util.List;

public class FabricsCheckMapper {

    public static FabricsCheckResponseDto toDto(
            FabricsCheck fc) {

        if (fc == null) {
            return null;
        }

        FabricsCheckResponseDto dto =
                new FabricsCheckResponseDto();

        dto.setId(fc.getId());

        dto.setCreatedAt(fc.getCreatedAt());

        dto.setOrderDbId(
                fc.getOrder().getId());

        dto.setOrderCode(
                fc.getOrderCode());

        dto.setPoNumber(
                fc.getPoNumber());

        dto.setTotalFabricRequired(
                fc.getTotalFabricRequired());

        if (fc.getStyle() != null) {

            dto.setStyleId(
                    fc.getStyle().getId());

            dto.setStyleCode(
                    fc.getStyle().getStyleCode());

            dto.setStyleName(
                    fc.getStyle().getStyleName());
        }

        List<FabricDetailsResponseDto> details =

                fc.getDetails()
                        .stream()
                        .map(FabricsCheckMapper::mapDetail)
                        .toList();

        dto.setDetails(details);

        return dto;
    }

    private static FabricDetailsResponseDto mapDetail(
            FabricDetails detail) {

        FabricDetailsResponseDto dto =
                new FabricDetailsResponseDto();

        dto.setId(detail.getId());
        dto.setProductName(detail.getProductName());
        dto.setSize(detail.getSize());
        dto.setType(detail.getType());
        dto.setBaseFabric(detail.getBaseFabric());
        dto.setQty(detail.getQty());
        dto.setCalculatedFabric(
                detail.getCalculatedFabric());
        dto.setHasUom(detail.getHasUom());

        return dto;
    }
}
