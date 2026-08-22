package com.emranhss.GarmentsManagementSystem.dto.request;


import com.emranhss.GarmentsManagementSystem.enums.Role;
import lombok.Data;

@Data
public class UserRequestDto {

    private String name;

    private String email;

    private String phone;

    private String password;

    private Role role;

    private Boolean active;
}
