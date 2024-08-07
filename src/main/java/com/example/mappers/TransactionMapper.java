package com.example.mappers;

import com.example.dtos.response.TransactionDTO;
import com.example.entity.Transaction;

public interface TransactionMapper {
    static TransactionDTO from(
            Transaction transaction
    ){
        return TransactionDTO.builder()
                .transactionId(transaction.getTransactionId())
                .transactionType(transaction.getTransactionType())
                .recipientName(transaction.getRecipientName())
                .recipientAccount(transaction.getRecipientAccount())
                .recipientBank(transaction.getRecipientBank())
                .amount(transaction.getAmount())
                .build();
    }
}
