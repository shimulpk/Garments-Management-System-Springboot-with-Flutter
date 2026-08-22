package com.emranhss.GarmentsManagementSystem.dto.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PackingPlanRequestDto {

//     User selects only Finishing Plan

    private Long finishingPlanId;


    private Integer pcsPerCarton;

    private String packingMethod;

    private String polyBagType;

    private Boolean hangTag;

    private String packingSupervisor;

    private LocalDate startDate;

    private LocalDate expectedShipmentDate;

}
