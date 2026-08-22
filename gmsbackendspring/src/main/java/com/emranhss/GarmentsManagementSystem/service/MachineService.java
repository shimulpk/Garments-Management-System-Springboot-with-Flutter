package com.emranhss.GarmentsManagementSystem.service;

import com.emranhss.GarmentsManagementSystem.dto.request.MachineRequestDto;
import com.emranhss.GarmentsManagementSystem.dto.response.MachineResponseDto;

import java.util.List;

public interface MachineService {

    MachineResponseDto create(MachineRequestDto request);

    MachineResponseDto update(Long id,
                              MachineRequestDto request);

    MachineResponseDto getById(Long id);

    List<MachineResponseDto> getAll();

    void delete(Long id);
}
