package com.emranhss.GarmentsManagementSystem.dto.response;

import lombok.Data;

@Data
public class VendorResponseDto {
    private Long id;

    private String companyName;

    private String contactPerson;

    private String phone;

    private String address;

    private Boolean active;
}
