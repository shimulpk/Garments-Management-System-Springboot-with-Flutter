package com.emranhss.GarmentsManagementSystem.dto.mapper;

import com.emranhss.GarmentsManagementSystem.dto.request.BuyerRequestDto;
import com.emranhss.GarmentsManagementSystem.dto.response.BuyerContactResponseDto;
import com.emranhss.GarmentsManagementSystem.dto.response.BuyerResponseDto;
import com.emranhss.GarmentsManagementSystem.entity.Buyer;

public class BuyerMapper {

    public static Buyer toEntity(BuyerRequestDto dto) {

        if (dto == null) {
            return null;
        }

        Buyer buyer = new Buyer();

        buyer.setBuyerCode(dto.getBuyerCode());
        buyer.setBuyerName(dto.getBuyerName());
        buyer.setCountry(dto.getCountry());
        buyer.setAddress(dto.getAddress());
        buyer.setWebsite(dto.getWebsite());
        buyer.setCurrency(dto.getCurrency());
        buyer.setPaymentTerms(dto.getPaymentTerms());

        return buyer;
    }

    public static BuyerResponseDto toDto(Buyer buyer) {

        if (buyer == null) {
            return null;
        }

        BuyerResponseDto dto = new BuyerResponseDto();

        dto.setId(buyer.getId());
        dto.setBuyerCode(buyer.getBuyerCode());
        dto.setBuyerName(buyer.getBuyerName());
        dto.setCountry(buyer.getCountry());
        dto.setAddress(buyer.getAddress());
        dto.setWebsite(buyer.getWebsite());
        dto.setCurrency(buyer.getCurrency());
        dto.setPaymentTerms(buyer.getPaymentTerms());
        dto.setActive(buyer.getActive());

        if (buyer.getContacts() != null) {

            dto.setContacts(

                    buyer.getContacts()
                            .stream()
                            .map(contact -> {

                                BuyerContactResponseDto contactDto =
                                        new BuyerContactResponseDto();

                                contactDto.setId(contact.getId());
                                contactDto.setContactName(contact.getContactName());
                                contactDto.setDesignation(contact.getDesignation());
                                contactDto.setEmail(contact.getEmail());
                                contactDto.setPhone(contact.getPhone());

                                return contactDto;
                            })
                            .toList()
            );
        }
        return dto;
    }
}
