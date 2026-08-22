package com.emranhss.GarmentsManagementSystem.dto.request;

import lombok.Data;

@Data
public class MachineRequestDto {

    private String machineId;

    private String machineName;

    private String type;

    private Long productionLineId;
}
