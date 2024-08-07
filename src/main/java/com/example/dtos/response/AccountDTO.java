package com.example.dtos.response;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record AccountDTO(
        String accountName,
        String accountNumber,
        BigDecimal accountBalance,
        String accountBank
        ) {
}
