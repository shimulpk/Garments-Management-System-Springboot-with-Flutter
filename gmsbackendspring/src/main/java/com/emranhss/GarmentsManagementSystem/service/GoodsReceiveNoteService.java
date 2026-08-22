package com.emranhss.GarmentsManagementSystem.service;

import com.emranhss.GarmentsManagementSystem.dto.request.GoodsReceiveNoteRequestDto;
import com.emranhss.GarmentsManagementSystem.dto.response.GoodsReceiveNoteResponseDto;

import java.util.List;

public interface GoodsReceiveNoteService {


    GoodsReceiveNoteResponseDto create(
            GoodsReceiveNoteRequestDto request);



    GoodsReceiveNoteResponseDto getById(
            Long id);



    List<GoodsReceiveNoteResponseDto> getAll();

}
