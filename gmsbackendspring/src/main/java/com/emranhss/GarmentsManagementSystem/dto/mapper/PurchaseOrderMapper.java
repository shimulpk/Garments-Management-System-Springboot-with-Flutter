package com.emranhss.GarmentsManagementSystem.dto.mapper;

import com.emranhss.GarmentsManagementSystem.dto.response.PurchaseOrderResponseDto;
import com.emranhss.GarmentsManagementSystem.entity.PurchaseOrder;

import java.util.stream.Collectors;

public class PurchaseOrderMapper {

    public static PurchaseOrderResponseDto toDto(
            PurchaseOrder entity) {

        PurchaseOrderResponseDto dto =
                new PurchaseOrderResponseDto();

        dto.setId(entity.getId());

        dto.setPoNo(entity.getPoNo());

        dto.setPoDate(entity.getPoDate());

        dto.setVendorId(
                entity.getVendor().getId());

        dto.setVendorName(
                entity.getVendor().getCompanyName());

        dto.setStoreRequisitionId(
                entity.getStoreRequisition().getId());

        dto.setRequisitionNo(
                entity.getStoreRequisition().getPrNo());

        dto.setStatus(
                entity.getStatus());

        dto.setGrandTotal(
                entity.getGrandTotal());

        dto.setRemarks(
                entity.getRemarks());

        dto.setItems(
                entity.getItems()
                        .stream()
                        .map(PurchaseOrderItemMapper::toDto)
                        .collect(Collectors.toList())
        );

        return dto;

    }

}
