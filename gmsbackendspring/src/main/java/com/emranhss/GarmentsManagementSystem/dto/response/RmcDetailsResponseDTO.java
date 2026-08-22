package com.emranhss.GarmentsManagementSystem.dto.response;

import lombok.Data;

@Data
public class RmcDetailsResponseDTO {

    private Long id;

    private String materialName;



    private String unit;

    private Double qtyPerPiece;

    private Double unitPrice;

    private Double totalQtyRequired;

    private Double totalMaterialCost;

}
