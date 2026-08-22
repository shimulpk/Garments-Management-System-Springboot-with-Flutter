package com.emranhss.GarmentsManagementSystem.controller;

import com.emranhss.GarmentsManagementSystem.dto.request.RmcCheckRequestDTO;
import com.emranhss.GarmentsManagementSystem.dto.response.RmcCheckResponseDTO;
import com.emranhss.GarmentsManagementSystem.service.RmcCheckService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/rmc-checks")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN','MERCHANDISER')")
public class RmcCheckController {

    private final RmcCheckService rmcCheckService;

    @PostMapping
    public ResponseEntity<RmcCheckResponseDTO> generate(
            @Valid @RequestBody RmcCheckRequestDTO request) {

        return ResponseEntity.ok(
                rmcCheckService.generate(request)
        );

    }


    @GetMapping("/{id}")
    public ResponseEntity<RmcCheckResponseDTO> getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                rmcCheckService.getById(id)
        );

    }


    @GetMapping
    public ResponseEntity<List<RmcCheckResponseDTO>> getAll() {

        return ResponseEntity.ok(
                rmcCheckService.getAll()
        );

    }



    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(
            @PathVariable Long id) {

        rmcCheckService.delete(id);

        return ResponseEntity.ok(
                "RMC Check Deleted Successfully"
        );

    }



}