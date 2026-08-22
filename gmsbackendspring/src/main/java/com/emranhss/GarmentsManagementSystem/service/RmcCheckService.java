package com.emranhss.GarmentsManagementSystem.service;

import com.emranhss.GarmentsManagementSystem.dto.request.RmcCheckRequestDTO;
import com.emranhss.GarmentsManagementSystem.dto.response.RmcCheckResponseDTO;

import java.util.List;

public interface RmcCheckService {

    RmcCheckResponseDTO generate(
            RmcCheckRequestDTO request);

    RmcCheckResponseDTO getById(
            Long id);

    List<RmcCheckResponseDTO> getAll();

    void delete(
            Long id);
}
