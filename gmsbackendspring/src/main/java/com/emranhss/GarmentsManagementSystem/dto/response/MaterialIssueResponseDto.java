package com.emranhss.GarmentsManagementSystem.dto.response;

import com.emranhss.GarmentsManagementSystem.enums.MaterialIssueStatus;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class MaterialIssueResponseDto {

    private Long id;

    private String issueNo;

    private LocalDate issueDate;

    private String department;

    private String requestedBy;

    private String remarks;

    private MaterialIssueStatus status;

    private List<MaterialIssueItemResponseDto> items;
}
