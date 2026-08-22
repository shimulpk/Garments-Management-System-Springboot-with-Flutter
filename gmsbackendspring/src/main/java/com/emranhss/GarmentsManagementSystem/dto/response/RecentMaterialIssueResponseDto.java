package com.emranhss.GarmentsManagementSystem.dto.response;


import com.emranhss.GarmentsManagementSystem.enums.MaterialIssueStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecentMaterialIssueResponseDto {

    private String issueNo;

    private LocalDate issueDate;

    private String department;

    private String requestedBy;

    private MaterialIssueStatus status;
}
