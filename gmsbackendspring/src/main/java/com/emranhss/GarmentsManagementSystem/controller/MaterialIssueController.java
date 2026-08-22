package com.emranhss.GarmentsManagementSystem.controller;

import com.emranhss.GarmentsManagementSystem.dto.request.MaterialIssueRequestDto;
import com.emranhss.GarmentsManagementSystem.dto.response.MaterialIssueResponseDto;
import com.emranhss.GarmentsManagementSystem.service.MaterialIssueService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/material-issues")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN','STORE_MANAGER')")
public class MaterialIssueController {

    private final MaterialIssueService materialIssueService;


    @PostMapping
    public ResponseEntity<MaterialIssueResponseDto> create(
            @RequestBody MaterialIssueRequestDto request) {

        return ResponseEntity.ok(
                materialIssueService.create(request)
        );

    }

    @GetMapping("/{id}")
    public ResponseEntity<MaterialIssueResponseDto> getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                materialIssueService.getById(id)
        );

    }

    @GetMapping
    public ResponseEntity<List<MaterialIssueResponseDto>> getAll() {

        return ResponseEntity.ok(
                materialIssueService.getAll()
        );

    }

}
