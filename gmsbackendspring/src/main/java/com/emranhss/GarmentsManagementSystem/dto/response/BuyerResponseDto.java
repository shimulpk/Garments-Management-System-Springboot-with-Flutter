package com.emranhss.GarmentsManagementSystem.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class BuyerResponseDto {

    private Long id;

    private String buyerCode;

    private String buyerName;

    private String country;

    private String address;

    private String website;

    private String currency;

    private String paymentTerms;

    private Boolean active;

    private List<BuyerContactResponseDto> contacts;
}
