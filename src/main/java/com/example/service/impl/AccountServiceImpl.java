package com.example.service.impl;

import com.example.constant.Message;
import com.example.dtos.request.FundAccountDTO;
import com.example.dtos.response.AccountDTO;
import com.example.entity.Account;
import com.example.entity.Bank;
import com.example.enums.AccountCurrency;
import com.example.exception.ResourceNotFoundException;
import com.example.mappers.AccountMapper;
import com.example.repository.AccountRepository;
import com.example.repository.BankRepository;
import com.example.service.AccountService;
import com.example.service.UserAccessService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final BankRepository bankRepository;
    private final UserAccessService userAccessService;


    @Override
    public Account createAccount() {
        String generatedAN = generateTenDigitAccountNumber();
        if(accountRepository.existsByAccountNumber(generatedAN))
            generatedAN = generateTenDigitAccountNumber();
        Bank bank = Bank.builder()
                .bankName("Excel Bank")
                .build();
        bankRepository.save(bank);
        return Account.builder()
                .accountNumber(generatedAN)
                .balance(BigDecimal.valueOf(0.00))
                .currency(AccountCurrency.N)
                .bank(bank)
                .build();
    }

    @Override
    public AccountDTO getAccountByUserId(long userId) {
        // Check if the authenticated user has access
        long accessId = userAccessService.authenticatedUserId();
        if(userId != accessId){
            throw new ResourceNotFoundException(
                    Message.ACCOUNT_NOT_FOUND.formatted(userId)
            );
        }
        return accountRepository.findByUserId(userId)
                .map(AccountMapper::from)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                Message.ACCOUNT_NOT_FOUND.formatted(userId)
                        )
                );
    }

    @Override
    public void fundAccount(long userId, FundAccountDTO request) {
        // Check if the authenticated user has access
        long accessId = userAccessService.authenticatedUserId();
        if(userId != accessId){
            throw new ResourceNotFoundException(
                    Message.ACCOUNT_NOT_FOUND.formatted(userId)
            );
        }
        // Find the account by userId
        Account account = accountRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        Message.ACCOUNT_NOT_FOUND.formatted(userId)
                ));

        // Update the account balance
        BigDecimal newAmount = account.getBalance().add(request.amount());
        account.setBalance(newAmount);
        accountRepository.save(account);
    }

    private String generateTenDigitAccountNumber(){
        return IntStream.generate(() -> new Random().nextInt(10))
                .limit(10)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining());
    }
}
