package com.emranhss.GarmentsManagementSystem.serviceimp;

import com.emranhss.GarmentsManagementSystem.dto.mapper.UserMapper;
import com.emranhss.GarmentsManagementSystem.dto.request.ChangePasswordRequestDto;
import com.emranhss.GarmentsManagementSystem.dto.request.ProfileUpdateRequestDto;
import com.emranhss.GarmentsManagementSystem.dto.request.UserRequestDto;
import com.emranhss.GarmentsManagementSystem.dto.response.UserResponseDto;
import com.emranhss.GarmentsManagementSystem.entity.User;
import com.emranhss.GarmentsManagementSystem.repository.UserRepository;
import com.emranhss.GarmentsManagementSystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;



    @Override
    public UserResponseDto create(UserRequestDto request) {
        // Check duplicate email
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists.");
        }

        // Check duplicate phone
        if (userRepository.existsByPhone(request.getPhone())) {
            throw new RuntimeException("Phone already exists.");
        }


        User user = userMapper.toEntity(request);

        // Password BCrypt Encode
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Save
        user = userRepository.save(user);

        // Entity → ResponseDTO
        return userMapper.toResponseDto(user);


}

    @Override
    public UserResponseDto update(Long id, UserRequestDto request) {
        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("User not found with id : " + id));

        if (!user.getEmail().equals(request.getEmail())
                && userRepository.existsByEmail(request.getEmail())) {

            throw new RuntimeException("Email already exists.");
        }

        if (!user.getPhone().equals(request.getPhone())
                && userRepository.existsByPhone(request.getPhone())) {

            throw new RuntimeException("Phone already exists.");
        }

        // Update entity
        userMapper.updateEntity(request, user);

        // Encode password only if changed
        if (request.getPassword() != null &&
                !request.getPassword().isBlank()) {

            user.setPassword(
                    passwordEncoder.encode(request.getPassword())
            );
        }

        user = userRepository.save(user);

        return userMapper.toResponseDto(user);
    }

    @Override
    public UserResponseDto getById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("User not found with id : " + id));

        return userMapper.toResponseDto(user);
    }

    @Override
    public List<UserResponseDto> getAll() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toResponseDto)
                .toList();
    }

    @Override
    public void delete(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("User not found with id : " + id));

        userRepository.delete(user);
    }

    @Override
    public UserResponseDto getMyProfile() {
        return userMapper.toResponseDto(getCurrentUser());
    }

    @Override
    public UserResponseDto updateMyProfile(ProfileUpdateRequestDto request) {
        User user = getCurrentUser();

        user.setName(request.getName());
        user.setPhone(request.getPhone());

        user = userRepository.save(user);

        return userMapper.toResponseDto(user);
    }

    @Override
    public void changePassword(ChangePasswordRequestDto request) {
        // Logged-in user's email
        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        // Find user
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Validation 1: New Password & Confirm Password
        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new RuntimeException("New password and confirm password do not match");
        }

        // Validation 2: Current Password
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new RuntimeException("Current password is incorrect");
        }

        // Validation 3: New Password should not be same as Current Password
        if (passwordEncoder.matches(request.getNewPassword(), user.getPassword())) {
            throw new RuntimeException("New password must be different from current password");
        }

        // Update Password
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        // Save
        userRepository.save(user);
    }


    private User getCurrentUser() {

        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        String email = authentication.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }


}
