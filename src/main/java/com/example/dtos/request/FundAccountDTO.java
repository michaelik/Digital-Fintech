package com.example.dtos.request;

import com.example.constant.Message;
import jakarta.validation.constraints.DecimalMin;

import java.math.BigDecimal;

public record FundAccountDTO(
        @DecimalMin(
                value = "0",
                inclusive = false,
                message = Message.INVALID_AMOUNT
        )
        BigDecimal amount
) {
}
