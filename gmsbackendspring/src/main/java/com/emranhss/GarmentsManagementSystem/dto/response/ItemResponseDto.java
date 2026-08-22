package com.emranhss.GarmentsManagementSystem.dto.response;

import lombok.Data;

@Data
public class ItemResponseDto {
    private Long id;

    private String itemName;

    private String category;

    private String unit;

    private Boolean active;
}
