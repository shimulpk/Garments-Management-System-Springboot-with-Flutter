package com.emranhss.GarmentsManagementSystem.dto.response;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class GoodsReceiveNoteResponseDto {

    private Long id;

    private String grnNo;

    private LocalDate grnDate;

    private Long purchaseOrderId;

    private String poNo;

    private String challanNo;

    private Double grandTotal;

    private String remarks;

    private List<GoodsReceiveNoteItemResponseDto> items;
}
