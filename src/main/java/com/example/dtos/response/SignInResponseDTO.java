package com.example.dtos.response;

import lombok.Builder;

@Builder
public record SignInResponseDTO(
        String token,
        UserDTO user
) {
}
