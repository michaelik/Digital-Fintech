package com.example.dtos.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record TransactionDTO(
        long transactionId,
        String transactionType,
        String recipientName,
        String recipientAccount,
        String recipientBank,
        BigDecimal amount
) {
}
