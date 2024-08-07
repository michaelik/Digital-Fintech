package com.example.dtos.response;

import lombok.Builder;

@Builder
public record BillerDTO(
        long id,
        String name
) {
}
