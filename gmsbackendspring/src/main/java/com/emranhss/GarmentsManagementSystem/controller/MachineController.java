package com.emranhss.GarmentsManagementSystem.controller;

import com.emranhss.GarmentsManagementSystem.dto.request.MachineRequestDto;
import com.emranhss.GarmentsManagementSystem.dto.response.MachineResponseDto;
import com.emranhss.GarmentsManagementSystem.service.MachineService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/machines")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN','PRODUCTION_MANAGER')")
public class MachineController {

    private final MachineService machineService;

    @PostMapping
    public ResponseEntity<MachineResponseDto> create(@RequestBody MachineRequestDto requestDto){
        return ResponseEntity.ok(machineService.create(requestDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MachineResponseDto> update(@PathVariable Long id, @RequestBody MachineRequestDto requestDto){
        return ResponseEntity.ok(machineService.update( id, requestDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MachineResponseDto> getById(@PathVariable Long id){
        return ResponseEntity.ok(machineService.getById(id));

    }

    @GetMapping
    public ResponseEntity<List<MachineResponseDto>> getAll(){
        return ResponseEntity.ok(machineService.getAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        machineService.delete(id);
        return ResponseEntity.ok("Deleted SuccessFully");
    }
}
