package com.example.dtos.response;

import com.example.enums.Gender;
import lombok.Builder;

import java.util.List;

@Builder
public record UserDTO(
        long id,
        String name,
        String email,
        Integer age,
        Gender gender,
        List<String> roles,
        String username
) {
}
