package com.example.assignmentt2009m1springboot.entity.dto;

import javax.validation.constraints.NotBlank;

public class LoginDto {
    @NotBlank(message = "Email is required")
    private String email;
    @NotBlank(message = "Password is required")
    private String password;
}
