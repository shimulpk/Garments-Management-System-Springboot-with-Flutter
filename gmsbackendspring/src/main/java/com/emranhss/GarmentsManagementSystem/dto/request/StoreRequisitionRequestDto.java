package com.emranhss.GarmentsManagementSystem.dto.request;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class StoreRequisitionRequestDto {

    private LocalDate requisitionDate;

    private String requestedBy;

    private String department;

    private String remarks;

    private List<StoreRequisitionItemRequestDto> items;
}
