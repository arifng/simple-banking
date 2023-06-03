package com.tuum.assignment.service.impl;

import com.tuum.assignment.config.MessagePublisher;
import com.tuum.assignment.exception.ApiException;
import com.tuum.assignment.dto.TransactionResponse;
import com.tuum.assignment.mapper.AccountMapper;
import com.tuum.assignment.mapper.TransactionMapper;
import com.tuum.assignment.model.Account;
import com.tuum.assignment.model.Balance;
import com.tuum.assignment.model.Transaction;
import com.tuum.assignment.service.BalanceService;
import com.tuum.assignment.service.TransactionService;
import com.tuum.assignment.util.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Rana on 27 May, 2023
 */
@Service
@Transactional
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final MessagePublisher publisher;
    private final BalanceService balanceService;
    private final AccountMapper accountMapper;
    private final TransactionMapper transactionMapper;

    @Override
    @Transactional
    public TransactionResponse createTransaction(Transaction transaction) throws ApiException {
        Balance balance = balanceService.updateAmountOfBalance(transaction);

        try {
            transactionMapper.insertTransaction(transaction);
        } catch (Exception exception) {
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR,
                    exception.getMessage());
        }

        Account account = accountMapper.findById(transaction.getAccountId())
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Account missing"));

        publisher.publishAccountDetails(account);

        return getTransactionResponse(transaction, balance.getAmount());
    }

    @Override
    public List<Transaction> getTransactions(long accountId) throws ApiException {
        accountMapper.findById(accountId)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Invalid account"));
        return transactionMapper.findByAccountId(accountId);
    }

    private TransactionResponse getTransactionResponse(Transaction transaction, BigDecimal balanceAfterTransaction) {
        return new TransactionResponse(transaction.getAccountId(), transaction.getId(),
                transaction.getAmount(), transaction.getCurrency(), transaction.getDirection(),
                transaction.getDescription(), balanceAfterTransaction);
    }

}
