package com.example.dtos.request;

import com.example.constant.Message;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record TransferFundDTO(
        @NotBlank(
                message = "account number is required"
        )
        @Pattern(
                regexp = "\\d{10}",
                message = "Account number must be exactly 10 digits")
        String accountNumber,
        @NotNull(
                message = "amount is required"
        )
        @DecimalMin(
                value = "0",
                inclusive = false,
                message = Message.INVALID_AMOUNT
        )
        BigDecimal amount
) {
}
