package com.emranhss.GarmentsManagementSystem.dto.mapper;

import com.emranhss.GarmentsManagementSystem.dto.response.StoreRequisitionResponseDto;
import com.emranhss.GarmentsManagementSystem.entity.StoreRequisition;

import java.util.stream.Collectors;

public class StoreRequisitionMapper {


    public static StoreRequisitionResponseDto toDto(
            StoreRequisition requisition) {

        if (requisition == null) {
            return null;
        }

        StoreRequisitionResponseDto dto =
                new StoreRequisitionResponseDto();

        dto.setId(requisition.getId());

        dto.setPrNo(requisition.getPrNo());

        dto.setRequisitionDate(requisition.getRequisitionDate());

        dto.setRequestedBy(requisition.getRequestedBy());

        dto.setDepartment(requisition.getDepartment());

        dto.setRemarks(requisition.getRemarks());

        dto.setStatus(requisition.getStatus());

        dto.setItems(
                requisition.getItems()
                        .stream()
                        .map(StoreRequisitionItemMapper::toDto)
                        .collect(Collectors.toList())
        );

        return dto;
    }
}
