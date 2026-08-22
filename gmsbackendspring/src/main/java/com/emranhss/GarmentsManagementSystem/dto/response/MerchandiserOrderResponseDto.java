package com.emranhss.GarmentsManagementSystem.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MerchandiserOrderResponseDto {

    private String orderNumber;

    private String buyerName;

    private String styleName;

    private Integer orderQuantity;

    private LocalDate shipDate;

    private String status;
}
