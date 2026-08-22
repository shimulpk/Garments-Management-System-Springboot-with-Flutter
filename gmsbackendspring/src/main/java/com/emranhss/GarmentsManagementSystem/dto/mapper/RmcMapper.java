package com.emranhss.GarmentsManagementSystem.dto.mapper;

import com.emranhss.GarmentsManagementSystem.dto.response.RmcCheckResponseDTO;
import com.emranhss.GarmentsManagementSystem.dto.response.RmcDetailsResponseDTO;
import com.emranhss.GarmentsManagementSystem.entity.RmcCheck;
import com.emranhss.GarmentsManagementSystem.entity.RmcDetails;

import java.util.List;



public class RmcMapper {

    public static RmcCheckResponseDTO toDto(
            RmcCheck entity) {

        if (entity == null) {
            return null;
        }

        RmcCheckResponseDTO dto =
                new RmcCheckResponseDTO();

        dto.setId(entity.getId());

        if (entity.getOrder() != null) {

            dto.setOrderId(
                    entity.getOrder().getId());

            dto.setOrderNumber(
                    entity.getOrder().getOrderId());

        }

        if (entity.getStyle() != null) {

            dto.setStyleId(
                    entity.getStyle().getId());

        }

        dto.setBuyerName(
                entity.getBuyerName());

        dto.setStyleName(
                entity.getStyleName());

        dto.setTotalOrderQty(
                entity.getTotalOrderQty());

        dto.setGrandTotalCost(
                entity.getGrandTotalCost());

        dto.setCreatedAt(
                entity.getCreatedAt());

        if (entity.getRmcDetailsList() != null) {

            List<RmcDetailsResponseDTO> details =

                    entity.getRmcDetailsList()
                            .stream()
                            .map(RmcMapper::mapDetails)
                            .toList();

            dto.setRmcDetailsList(details);

        }

        return dto;

    }

    private static RmcDetailsResponseDTO mapDetails(
            RmcDetails detail) {

        RmcDetailsResponseDTO dto =
                new RmcDetailsResponseDTO();

        dto.setId(detail.getId());

        dto.setMaterialName(
                detail.getMaterialName());



        dto.setUnit(
                detail.getUnit());

        dto.setQtyPerPiece(
                detail.getQtyPerPiece());

        dto.setUnitPrice(
                detail.getUnitPrice());

        dto.setTotalQtyRequired(
                detail.getTotalQtyRequired());

        dto.setTotalMaterialCost(
                detail.getTotalMaterialCost());

        return dto;

    }

}
