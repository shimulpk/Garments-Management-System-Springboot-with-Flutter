package com.emranhss.GarmentsManagementSystem.dto.request;

import lombok.Data;

@Data
public class BomStyleRequestDto {
//new Add
    private Long buyerId;

    private String styleCode;

    private String styleName;

    private String styleType;

    private String description;

    private String approvalStatus;

    private String sizeSet;
}
