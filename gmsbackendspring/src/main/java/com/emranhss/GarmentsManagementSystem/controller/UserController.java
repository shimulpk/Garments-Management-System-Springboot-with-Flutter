package com.emranhss.GarmentsManagementSystem.controller;

import com.emranhss.GarmentsManagementSystem.dto.request.ChangePasswordRequestDto;
import com.emranhss.GarmentsManagementSystem.dto.request.ProfileUpdateRequestDto;
import com.emranhss.GarmentsManagementSystem.dto.request.UserRequestDto;
import com.emranhss.GarmentsManagementSystem.dto.response.UserResponseDto;
import com.emranhss.GarmentsManagementSystem.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDto> create(@RequestBody UserRequestDto request) {
        return ResponseEntity.ok(userService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> update(@PathVariable Long id, @RequestBody UserRequestDto request) {
        return ResponseEntity.ok(userService.update(id, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAll() {
        return ResponseEntity.ok(userService.getAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.ok("User deleted successfully");
    }


    // =====================================
// Logged-in User Profile
// =====================================

    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> getMyProfile() {

        return ResponseEntity.ok(
                userService.getMyProfile()
        );

    }


    @PutMapping("/me")
    public ResponseEntity<UserResponseDto> updateMyProfile(
            @RequestBody ProfileUpdateRequestDto request
    ) {

        return ResponseEntity.ok(
                userService.updateMyProfile(request)
        );

    }

    @PutMapping("/change-password")
    public ResponseEntity<String> changePassword(
            @Valid @RequestBody ChangePasswordRequestDto request) {

        userService.changePassword(request);

        return ResponseEntity.ok("Password changed successfully");
    }
}
