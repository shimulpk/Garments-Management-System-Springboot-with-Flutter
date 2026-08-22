package com.emranhss.GarmentsManagementSystem.dto.mapper;

import com.emranhss.GarmentsManagementSystem.dto.response.GoodsReceiveNoteItemResponseDto;
import com.emranhss.GarmentsManagementSystem.dto.response.GoodsReceiveNoteResponseDto;
import com.emranhss.GarmentsManagementSystem.entity.GoodsReceiveNote;
import com.emranhss.GarmentsManagementSystem.entity.GoodsReceiveNoteItem;

import java.util.List;

public class GoodsReceiveNoteMapper {

    public static GoodsReceiveNoteResponseDto toDto(
            GoodsReceiveNote entity) {

        GoodsReceiveNoteResponseDto dto =
                new GoodsReceiveNoteResponseDto();

        dto.setId(entity.getId());

        dto.setGrnNo(entity.getGrnNo());

        dto.setGrnDate(entity.getGrnDate());

        dto.setPurchaseOrderId(
                entity.getPurchaseOrder().getId());

        dto.setPoNo(
                entity.getPurchaseOrder().getPoNo());

        dto.setChallanNo(
                entity.getChallanNo());

        dto.setGrandTotal(
                entity.getGrandTotal());

        dto.setRemarks(
                entity.getRemarks());

        List<GoodsReceiveNoteItemResponseDto> items =
                entity.getItems()
                        .stream()
                        .map(GoodsReceiveNoteMapper::toItemDto)
                        .toList();

        dto.setItems(items);

        return dto;

    }

    public static GoodsReceiveNoteItemResponseDto toItemDto(
            GoodsReceiveNoteItem entity) {

        GoodsReceiveNoteItemResponseDto dto =
                new GoodsReceiveNoteItemResponseDto();

        dto.setId(entity.getId());

        dto.setPurchaseOrderItemId(
                entity.getPurchaseOrderItem().getId());

        dto.setItemId(
                entity.getPurchaseOrderItem()
                        .getItem()
                        .getId());

        dto.setItemName(
                entity.getPurchaseOrderItem()
                        .getItem()
                        .getItemName());

        dto.setUnit(
                entity.getPurchaseOrderItem()
                        .getItem()
                        .getUnit());

        dto.setQuantity(
                entity.getQuantity());

        dto.setUnitPrice(
                entity.getUnitPrice());

        dto.setLineTotal(
                entity.getLineTotal());

        return dto;

    }

}
