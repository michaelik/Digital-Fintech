package com.example.service.impl;

import com.example.constant.Message;
import com.example.dtos.request.TransferFundDTO;
import com.example.dtos.response.TransactionDTO;
import com.example.entity.Account;
import com.example.entity.Transaction;
import com.example.exception.IllegalTransferException;
import com.example.exception.InsufficientBalanceException;
import com.example.exception.ResourceNotFoundException;
import com.example.mappers.TransactionMapper;
import com.example.repository.AccountRepository;
import com.example.repository.TransactionRepository;
import com.example.service.TransactionService;
import com.example.service.UserAccessService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final UserAccessService userAccessService;
    @Override
    public void transferFund(TransferFundDTO request) {
        long accessId = userAccessService.authenticatedUserId();
        Account debitAccount = getAccountByUserId(accessId);
        Account recipientAccount = getAccountByAccountNumber(request.accountNumber());

        validateTransfer(debitAccount, recipientAccount, request.amount());

        performTransfer(debitAccount, recipientAccount, request.amount());

        recordTransaction(debitAccount, recipientAccount, request.amount());
    }

    @Override
    public Page<TransactionDTO> getTransactions(int page, int size) {
        long accessId = userAccessService.authenticatedUserId();
        Account account = getAccountByUserId(accessId);
        Pageable pageable = PageRequest.of(page, size);
        return transactionRepository.findAllByAccount(account, pageable)
                .map(TransactionMapper::from);
    }

    private Account getAccountByUserId(long userId) {
        return accountRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        Message.ERROR_OCCURRED_WHILE_PROCESSING_TRANSFER
                ));
    }

    private Account getAccountByAccountNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new ResourceNotFoundException(
                        Message.ACCOUNT_NUMBER_NOT_FOUND.formatted(accountNumber)
                ));
    }

    private void validateTransfer(
            Account debitAccount,
            Account recipientAccount,
            BigDecimal amount
    ) {
        if (recipientAccount.getUser().equals(debitAccount.getUser())) {
            throw new IllegalTransferException(Message.INVALID_ACCOUNT_NUMBER);
        }

        if (debitAccount.getBalance().compareTo(amount) < 0) {
            throw new InsufficientBalanceException(Message.INSUFFICIENT_FUND);
        }
    }

    private void performTransfer(
            Account debitAccount,
            Account recipientAccount,
            BigDecimal amount
    ) {
        debitAccount.setBalance(debitAccount.getBalance().subtract(amount));
        recipientAccount.setBalance(recipientAccount.getBalance().add(amount));
        accountRepository.save(debitAccount);
        accountRepository.save(recipientAccount);
    }

    private void recordTransaction(
            Account debitAccount,
            Account recipientAccount,
            BigDecimal amount
    ) {
        String name = recipientAccount.getUser().getFirstName()+" "+recipientAccount.getUser().getLastName();
        Transaction debitTransaction = Transaction.builder()
                .recipientName(name)
                .recipientAccount(recipientAccount.getAccountNumber())
                .recipientBank(recipientAccount.getBank().getBankName())
                .amount(amount)
                .transactionType("Transfer")
                .currentBalance(debitAccount.getBalance())
                .account(debitAccount)
                .user(debitAccount.getUser())
                .build();

        Transaction creditTransaction = Transaction.builder()
                .amount(amount)
                .transactionType("Deposit")
                .currentBalance(recipientAccount.getBalance())
                .account(recipientAccount)
                .user(recipientAccount.getUser())
                .build();

        transactionRepository.save(debitTransaction);
        transactionRepository.save(creditTransaction);
    }
}
