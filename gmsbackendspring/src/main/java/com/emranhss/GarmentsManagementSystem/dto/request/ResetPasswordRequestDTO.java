package com.emranhss.GarmentsManagementSystem.dto.request;

import lombok.Data;

@Data
public class ResetPasswordRequestDTO {
    private String token;        // from email link
    private String newPassword;
}
