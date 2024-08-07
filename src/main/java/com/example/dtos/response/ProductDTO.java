package com.example.dtos.response;

import lombok.Builder;

@Builder
public record ProductDTO(
        long id,
        String name
) {
}
