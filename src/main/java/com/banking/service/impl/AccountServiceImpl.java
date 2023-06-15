package com.banking.service.impl;

import com.banking.config.MessagePublisher;
import com.banking.dto.AccountResponse;
import com.banking.dto.BalanceResponse;
import com.banking.exception.ApiException;
import com.banking.mapper.AccountMapper;
import com.banking.mapper.BalanceMapper;
import com.banking.model.Account;
import com.banking.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.stream.Collectors;

/**
 * Created by Rana on 27 May, 2023
 */
@Service
@Transactional
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final MessagePublisher publisher;
    private final AccountMapper accountMapper;
    private final BalanceMapper balanceMapper;

    @Override
    @Transactional
    public AccountResponse createAccount(Account account) {
        accountMapper.insertAccount(account);
        account.getBalances().forEach(balance -> {
            balance.setAccountId(account.getId());
            balance.setAmount(BigDecimal.ZERO);
        });

        balanceMapper.insertBalances(account.getBalances());
        publisher.publishAccountDetails(account);

        return getAccountResponse(account);
    }

    @Override
    public AccountResponse getAccount(long accountId) throws ApiException {
        Account account = accountMapper.findById(accountId)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Account not found"));
        return getAccountResponse(account);
    }

    private AccountResponse getAccountResponse(Account account) {
        return new AccountResponse(account.getId(), account.getCustomerId(),
                account.getBalances()
                        .stream()
                        .map(balance -> new BalanceResponse(balance.getCurrency(), balance.getAmount()))
                        .collect(Collectors.toList()));
    }
}
