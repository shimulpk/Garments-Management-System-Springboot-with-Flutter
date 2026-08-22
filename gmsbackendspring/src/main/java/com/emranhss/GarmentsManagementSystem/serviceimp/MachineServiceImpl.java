package com.emranhss.GarmentsManagementSystem.serviceimp;

import com.emranhss.GarmentsManagementSystem.dto.mapper.MachineMapper;
import com.emranhss.GarmentsManagementSystem.dto.request.MachineRequestDto;
import com.emranhss.GarmentsManagementSystem.dto.response.MachineResponseDto;
import com.emranhss.GarmentsManagementSystem.entity.Machine;
import com.emranhss.GarmentsManagementSystem.entity.ProductionLine;
import com.emranhss.GarmentsManagementSystem.repository.MachineRepository;
import com.emranhss.GarmentsManagementSystem.repository.ProductionLineRepository;
import com.emranhss.GarmentsManagementSystem.service.MachineService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MachineServiceImpl implements MachineService {
    private final MachineRepository machineRepository;
    private final ProductionLineRepository productionLineRepository;


    @Override
    public MachineResponseDto create(MachineRequestDto request) {


        ProductionLine productionLine = productionLineRepository.findById(request.getProductionLineId())
                .orElseThrow(() -> new RuntimeException("Production Line Not Found"));

        Machine machine =
                MachineMapper.toEntity(request);

        machine.setProductionLine(productionLine);
        machine.setActive(true);

        return MachineMapper.toDto(
                machineRepository.save(machine));
    }

    @Override
    public MachineResponseDto update(Long id, MachineRequestDto request) {
        Machine machine =
                machineRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Machine Not Found"));

        ProductionLine productionLine = productionLineRepository.findById(request.getProductionLineId())
                .orElseThrow(() -> new RuntimeException("Production Line Not Found"));

        machine.setMachineId(
                request.getMachineId());

        machine.setMachineName(
                request.getMachineName());

        machine.setType(
                request.getType());

        machine.setProductionLine(productionLine);


        return MachineMapper.toDto(
                machineRepository.save(machine));
    }

    @Override
    public MachineResponseDto getById(Long id) {
        return MachineMapper.toDto(
                machineRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Machine Not Found")));
    }

    @Override
    public List<MachineResponseDto> getAll() {
        return machineRepository.findAll()
                .stream()
                .map(MachineMapper::toDto)
                .toList();
    }

    @Override
    public void delete(Long id) {
        machineRepository.deleteById(id);
    }
}
