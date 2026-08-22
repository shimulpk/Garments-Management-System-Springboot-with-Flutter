package com.emranhss.GarmentsManagementSystem.dto.mapper;

import com.emranhss.GarmentsManagementSystem.dto.request.VendorRequestDto;
import com.emranhss.GarmentsManagementSystem.dto.response.VendorResponseDto;
import com.emranhss.GarmentsManagementSystem.entity.Vendor;

public class VendorMapper {

    public static Vendor toEntity(
            VendorRequestDto dto) {

        if (dto == null) {
            return null;
        }

        Vendor vendor = new Vendor();

        vendor.setCompanyName(
                dto.getCompanyName());

        vendor.setContactPerson(
                dto.getContactPerson());

        vendor.setPhone(
                dto.getPhone());

        vendor.setAddress(
                dto.getAddress());

        return vendor;
    }

    public static VendorResponseDto toDto(
            Vendor vendor) {

        if (vendor == null) {
            return null;
        }

        VendorResponseDto dto =
                new VendorResponseDto();

        dto.setId(vendor.getId());

        dto.setCompanyName(
                vendor.getCompanyName());

        dto.setContactPerson(
                vendor.getContactPerson());

        dto.setPhone(
                vendor.getPhone());

        dto.setAddress(
                vendor.getAddress());

        dto.setActive(
                vendor.getActive());

        return dto;
    }
}
