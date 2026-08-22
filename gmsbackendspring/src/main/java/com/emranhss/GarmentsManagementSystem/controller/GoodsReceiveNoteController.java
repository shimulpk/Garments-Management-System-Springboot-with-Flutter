package com.emranhss.GarmentsManagementSystem.controller;


import com.emranhss.GarmentsManagementSystem.dto.request.GoodsReceiveNoteRequestDto;
import com.emranhss.GarmentsManagementSystem.dto.response.GoodsReceiveNoteResponseDto;
import com.emranhss.GarmentsManagementSystem.service.GoodsReceiveNoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/grns")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN','STORE_MANAGER')")
public class GoodsReceiveNoteController {

    private final GoodsReceiveNoteService goodsReceiveNoteService;

    @PostMapping
    public GoodsReceiveNoteResponseDto create(
            @RequestBody GoodsReceiveNoteRequestDto request) {

        return goodsReceiveNoteService.create(request);

    }

    // =====================================
    // Get All GRNs
    // =====================================

    @GetMapping
    public List<GoodsReceiveNoteResponseDto> getAll() {

        return goodsReceiveNoteService.getAll();

    }

    // =====================================
    // Get GRN By Id
    // =====================================

    @GetMapping("/{id}")
    public GoodsReceiveNoteResponseDto getById(
            @PathVariable Long id) {

        return goodsReceiveNoteService.getById(id);

    }


}
