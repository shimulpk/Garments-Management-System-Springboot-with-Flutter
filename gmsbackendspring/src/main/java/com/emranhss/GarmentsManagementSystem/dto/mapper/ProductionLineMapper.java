package com.emranhss.GarmentsManagementSystem.dto.mapper;

import com.emranhss.GarmentsManagementSystem.dto.request.ProductionLineRequestDto;
import com.emranhss.GarmentsManagementSystem.dto.response.ProductionLineResponseDto;
import com.emranhss.GarmentsManagementSystem.entity.ProductionLine;

public class ProductionLineMapper {
    public static ProductionLine toEntity(
            ProductionLineRequestDto dto){

        if(dto == null){
            return null;
        }

        ProductionLine line =
                new ProductionLine();

        line.setLineId(dto.getLineId());
        line.setCapacityPerDay(dto.getCapacityPerDay());
        line.setSupervisor(dto.getSupervisor());

        return line;
    }

    public static ProductionLineResponseDto toDto(
            ProductionLine line){

        if(line == null){
            return null;
        }

        ProductionLineResponseDto dto =
                new ProductionLineResponseDto();

        dto.setId(line.getId());
        dto.setLineId(line.getLineId());
        dto.setCapacityPerDay(line.getCapacityPerDay());
        dto.setSupervisor(line.getSupervisor());

        dto.setMachineCount(
                line.getMachines() == null
                        ? 0
                        : line.getMachines().size());

        return dto;
    }
}
