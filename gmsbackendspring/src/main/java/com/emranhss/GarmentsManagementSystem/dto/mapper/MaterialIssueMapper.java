package com.emranhss.GarmentsManagementSystem.dto.mapper;

import com.emranhss.GarmentsManagementSystem.dto.response.MaterialIssueResponseDto;
import com.emranhss.GarmentsManagementSystem.entity.MaterialIssue;

import java.util.stream.Collectors;

public class MaterialIssueMapper {

    public static MaterialIssueResponseDto toDto(MaterialIssue entity) {

        if (entity == null) {
            return null;
        }

        MaterialIssueResponseDto dto = new MaterialIssueResponseDto();

        dto.setId(entity.getId());

        dto.setIssueNo(entity.getIssueNo());

        dto.setIssueDate(entity.getIssueDate());

        dto.setDepartment(entity.getDepartment());

        dto.setRequestedBy(entity.getRequestedBy());

        dto.setRemarks(entity.getRemarks());

        dto.setStatus(entity.getStatus());

        dto.setItems(
                entity.getItems()
                        .stream()
                        .map(MaterialIssueItemMapper::toDto)
                        .collect(Collectors.toList())
        );

        return dto;

    }

}
