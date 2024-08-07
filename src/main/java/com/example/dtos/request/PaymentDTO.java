package com.example.dtos.request;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record PaymentDTO(
    long categoryId,
    long billerId,
    long productId,
    BigDecimal amount){

}
