package com.emranhss.GarmentsManagementSystem.service;

import com.emranhss.GarmentsManagementSystem.dto.request.ChangePasswordRequestDto;
import com.emranhss.GarmentsManagementSystem.dto.request.ProfileUpdateRequestDto;
import com.emranhss.GarmentsManagementSystem.dto.request.UserRequestDto;
import com.emranhss.GarmentsManagementSystem.dto.response.UserResponseDto;

import java.util.List;

public interface UserService {

    UserResponseDto create(UserRequestDto request);


    UserResponseDto update(Long id, UserRequestDto request);

    UserResponseDto getById(Long id);


    List<UserResponseDto> getAll();

  
    void delete(Long id);

    UserResponseDto getMyProfile();

    UserResponseDto updateMyProfile(ProfileUpdateRequestDto request);

    void changePassword(ChangePasswordRequestDto request);
}
