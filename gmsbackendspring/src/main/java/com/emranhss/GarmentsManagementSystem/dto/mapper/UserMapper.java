package com.emranhss.GarmentsManagementSystem.dto.mapper;

import com.emranhss.GarmentsManagementSystem.dto.request.UserRequestDto;
import com.emranhss.GarmentsManagementSystem.dto.response.UserResponseDto;
import com.emranhss.GarmentsManagementSystem.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toEntity(UserRequestDto dto) {

        User user = new User();

        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());

        // Password akhane plain text thakbe
        // Service layer-এ BCrypt encodekora hobe
        user.setPassword(dto.getPassword());

        user.setRole(dto.getRole());

        user.setActive(
                dto.getActive() != null ? dto.getActive() : true
        );

        return user;
    }

    /**
     * Entity → Response DTO
     */
    public UserResponseDto toResponseDto(User user) {

        UserResponseDto dto = new UserResponseDto();

        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());
        dto.setRole(user.getRole());
        dto.setActive(user.isActive());

        return dto;
    }

    /**
     * Update Existing Entity
     */
    public void updateEntity(UserRequestDto dto, User user) {

        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());

        // Password update only if provided
        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            user.setPassword(dto.getPassword());
        }

        user.setRole(dto.getRole());

        if (dto.getActive() != null) {
            user.setActive(dto.getActive());
        }
    }


}
