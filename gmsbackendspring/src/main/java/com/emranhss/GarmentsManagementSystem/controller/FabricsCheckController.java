package com.emranhss.GarmentsManagementSystem.controller;

import com.emranhss.GarmentsManagementSystem.dto.request.FabricsCheckRequestDto;
import com.emranhss.GarmentsManagementSystem.dto.response.FabricsCheckResponseDto;
import com.emranhss.GarmentsManagementSystem.service.FabricsCheckService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fabric-check")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN','MERCHANDISER','CUTTING_MANAGER')")
public class FabricsCheckController {

    private final FabricsCheckService fabricsCheckService;

    @PostMapping("/generate")
    public ResponseEntity<
            FabricsCheckResponseDto>
    generate(
            @RequestBody
            FabricsCheckRequestDto request) {

        return ResponseEntity.ok(
                fabricsCheckService
                        .generate(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<
            FabricsCheckResponseDto>
    getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                fabricsCheckService
                        .getById(id));
    }

    @GetMapping
    public ResponseEntity<
            List<FabricsCheckResponseDto>>
    getAll() {

        return ResponseEntity.ok(
                fabricsCheckService
                        .getAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String>
    delete(
            @PathVariable Long id) {

        fabricsCheckService.delete(id);

        return ResponseEntity.ok(
                "FC Deleted Successfully");
    }


    @GetMapping("/order/{orderId}")
    public ResponseEntity<FabricsCheckResponseDto> getByOrder(
            @PathVariable Long orderId) {

        return ResponseEntity.ok(
                fabricsCheckService.getByOrder(orderId));

    }


}
