package com.emranhss.GarmentsManagementSystem.dto.mapper;

import com.emranhss.GarmentsManagementSystem.dto.response.PurchaseOrderItemResponseDto;
import com.emranhss.GarmentsManagementSystem.entity.PurchaseOrderItem;

public class PurchaseOrderItemMapper {

    public static PurchaseOrderItemResponseDto toDto(
            PurchaseOrderItem entity) {

        PurchaseOrderItemResponseDto dto =
                new PurchaseOrderItemResponseDto();

        dto.setId(entity.getId());

        dto.setItemId(
                entity.getItem().getId());

        dto.setItemName(
                entity.getItem().getItemName());

        dto.setUnit(
                entity.getItem().getUnit());

        dto.setQuantity(
                entity.getQuantity());

        dto.setUnitPrice(
                entity.getUnitPrice());

        dto.setLineTotal(
                entity.getLineTotal());

        return dto;

    }
}
