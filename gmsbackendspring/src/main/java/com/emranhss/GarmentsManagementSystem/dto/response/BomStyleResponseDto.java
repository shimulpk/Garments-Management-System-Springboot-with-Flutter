package com.emranhss.GarmentsManagementSystem.dto.response;

import lombok.Data;

@Data
public class BomStyleResponseDto {
    private Long id;
//new Add
    private Long buyerId;

    private String buyerCode;

    private String buyerName;
//    ai porjonto

    private String styleCode;

    private String styleName;

    private String styleType;

    private String description;

    private String approvalStatus;

    private String sizeSet;

    private Boolean active;
}
