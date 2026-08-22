package com.emranhss.GarmentsManagementSystem.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RmcCheckRequestDTO {

    @NotNull(message = "Order Selection is required")
    private Long orderId;


}
