package com.example.service;

import com.example.dtos.request.TransferFundDTO;
import com.example.dtos.response.TransactionDTO;
import org.springframework.data.domain.Page;

public interface TransactionService {
    void transferFund(TransferFundDTO request);
    Page<TransactionDTO> getTransactions(int page, int size);
}
