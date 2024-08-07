package com.example.dtos.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record SignInRequestDTO(
        @NotBlank(message = "please enter username or email")
        String username,
        @NotBlank(message = "password should not be empty")
        String password
) {
}
