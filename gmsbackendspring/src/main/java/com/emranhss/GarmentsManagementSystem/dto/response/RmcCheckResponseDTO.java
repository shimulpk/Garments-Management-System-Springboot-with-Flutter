package com.emranhss.GarmentsManagementSystem.dto.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class RmcCheckResponseDTO {

    private Long id;

    private Long orderId;

    private String orderNumber;

    private Long styleId;

    private String buyerName;

    private String styleName;

    private Integer totalOrderQty;

    private Double grandTotalCost;

    private LocalDateTime createdAt;

    private List<RmcDetailsResponseDTO> rmcDetailsList;
}
