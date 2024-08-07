package com.example.mappers;

import com.example.dtos.response.AccountDTO;
import com.example.entity.Account;

public interface AccountMapper {

    static AccountDTO from(
            Account account
    ){
        String name = account.getUser().getFirstName()+" "+account.getUser().getLastName();
        return AccountDTO.builder()
                .accountName(name)
                .accountNumber(account.getAccountNumber())
                .accountBalance(account.getBalance())
                .accountBank(account.getBank().getBankName())
                .build();
    }

}
