package com.example.dtos.response;

import lombok.Builder;

@Builder
public record CategoryDTO(
        long id,
        String name
) {
}
