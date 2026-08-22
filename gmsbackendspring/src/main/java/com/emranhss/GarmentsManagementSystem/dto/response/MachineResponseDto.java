package com.emranhss.GarmentsManagementSystem.dto.response;

import lombok.Data;

@Data
public class MachineResponseDto {

    private Long id;

    private String machineId;

    private String machineName;

    private String type;

    private Long productionLineId;
    private String lineId;

    private Boolean active;
}
