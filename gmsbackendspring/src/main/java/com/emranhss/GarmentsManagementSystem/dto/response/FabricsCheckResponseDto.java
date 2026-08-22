package com.emranhss.GarmentsManagementSystem.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class FabricsCheckResponseDto {
    private Long id;

    private Long orderDbId;

    private String orderCode;

    private String poNumber;

    private Long styleId;

    private String styleCode;

    private String styleName;

    private LocalDateTime createdAt;

    private BigDecimal totalFabricRequired;

    private List<FabricDetailsResponseDto> details;
}
