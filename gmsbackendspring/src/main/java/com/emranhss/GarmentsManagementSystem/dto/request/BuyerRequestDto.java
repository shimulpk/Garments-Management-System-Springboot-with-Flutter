package com.emranhss.GarmentsManagementSystem.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class BuyerRequestDto {

    private String buyerCode;

    private String buyerName;

    private String country;

    private String address;

    private String website;

    private String currency;

    private String paymentTerms;

    private List<BuyerContactRequestDto> contacts;
}
