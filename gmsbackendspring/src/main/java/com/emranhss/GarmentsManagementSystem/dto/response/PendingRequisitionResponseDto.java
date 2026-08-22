package com.emranhss.GarmentsManagementSystem.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PendingRequisitionResponseDto {

    private String prNo;

    private LocalDate requisitionDate;

    private String requestedBy;

    private String department;
}
