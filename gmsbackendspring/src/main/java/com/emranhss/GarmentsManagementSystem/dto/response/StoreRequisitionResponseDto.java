package com.emranhss.GarmentsManagementSystem.dto.response;

import com.emranhss.GarmentsManagementSystem.enums.StoreRequisitionStatus;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class StoreRequisitionResponseDto {

    private Long id;

    private String prNo;

    private LocalDate requisitionDate;

    private String requestedBy;

    private String department;

    private String remarks;

    private StoreRequisitionStatus status;

    private List<StoreRequisitionItemResponseDto> items;
}
