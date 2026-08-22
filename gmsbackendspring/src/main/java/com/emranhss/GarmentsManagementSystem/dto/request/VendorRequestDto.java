package com.emranhss.GarmentsManagementSystem.dto.request;

import lombok.Data;

@Data
public class VendorRequestDto {
    private String companyName;

    private String contactPerson;

    private String phone;

    private String address;
}
