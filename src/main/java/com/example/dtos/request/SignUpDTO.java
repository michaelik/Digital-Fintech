package com.example.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

@Builder
public record SignUpDTO(
        @NotNull(
                message = "first name is required"
        )
        String firstName,
        @NotNull(
                message = "last name is required"
        )
        String lastName,
        @Email
        @NotBlank(
                message = "email is required"
        )
        String email,
        @NotNull(
                message = "password is required"
        )
        String password,
        @NotNull(
                message = "age is required"
        )
        Integer age,
        @NotBlank(
                message = "gender is required"
        )
        @Pattern(
                regexp = "[MF]",
                message = "Only currency M and F is accepted"
        )
        String gender,
        @NotNull(
                message = "BVN is required"
        )
        @Pattern(
                regexp = "\\d{11}",
                message = "BVN must be a valid BVN number"
        )
        String bvn
) {
}
