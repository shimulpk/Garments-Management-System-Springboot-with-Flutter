package com.emranhss.GarmentsManagementSystem.dto.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class GoodsReceiveNoteRequestDto {

    private LocalDate grnDate;

    private Long purchaseOrderId;

    private String challanNo;

    private String remarks;
}
