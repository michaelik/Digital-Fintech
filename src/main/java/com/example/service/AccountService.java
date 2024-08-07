package com.example.service;

import com.example.dtos.request.FundAccountDTO;
import com.example.dtos.response.AccountDTO;
import com.example.entity.Account;

public interface AccountService {
    Account createAccount();

    AccountDTO getAccountByUserId(long userId);

    void fundAccount(long userId, FundAccountDTO request);
}
