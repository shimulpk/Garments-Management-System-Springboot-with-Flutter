package com.emranhss.GarmentsManagementSystem.service;

import com.emranhss.GarmentsManagementSystem.dto.request.MaterialIssueRequestDto;
import com.emranhss.GarmentsManagementSystem.dto.response.MaterialIssueResponseDto;

import java.util.List;

public interface MaterialIssueService {

    MaterialIssueResponseDto create(MaterialIssueRequestDto requestDto);

    List<MaterialIssueResponseDto> getAll();

    MaterialIssueResponseDto getById(Long id);
}
