package com.emranhss.GarmentsManagementSystem.dto.request;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class MaterialIssueRequestDto {

    private LocalDate issueDate;

    private String department;

    private String requestedBy;

    private String remarks;

    private List<MaterialIssueItemRequestDto> items;
}
