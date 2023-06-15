package com.banking.service;

import com.banking.UnitTestHelper;
import com.banking.config.MessagePublisher;
import com.banking.dto.AccountResponse;
import com.banking.dto.BalanceResponse;
import com.banking.exception.ApiException;
import com.banking.mapper.AccountMapper;
import com.banking.mapper.BalanceMapper;
import com.banking.model.Account;
import com.banking.service.impl.AccountServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Created by Rana on 03 Jun, 2023
 */
@ExtendWith(SpringExtension.class)
public class AccountServiceTests {
    @MockBean
    private MessagePublisher publisher;

    @MockBean
    private AccountMapper accountMapper;

    @MockBean
    private BalanceMapper balanceMapper;

    private AccountService accountService;

    private Account account;

    @BeforeEach
    void setUp() {
        accountService = new AccountServiceImpl(publisher, accountMapper, balanceMapper);
        account = UnitTestHelper.getAccount();
    }

    @Test
    void createAccountWillCallInsertAccountOfMapper() {
        try {
            accountService.createAccount(account);
            Mockito.verify(accountMapper, Mockito.times(1)).insertAccount(account);
        } catch (ApiException exception) {
            exception.printStackTrace();
        }
    }

    @Test
    void createAccountWillCallInsertBalanceOnBalanceMapper() {
        try {
            accountService.createAccount(account);
            Mockito.verify(balanceMapper, Mockito.times(1)).insertBalances(account.getBalances());
        } catch (ApiException exception) {
            exception.printStackTrace();
        }
    }

    @Test
    void createAccountWillPublishMessage() {
        try {
            accountService.createAccount(account);
            Mockito.verify(publisher, Mockito.times(1)).publishAccountDetails(account);
        } catch (ApiException exception) {
            exception.printStackTrace();
        }
    }

    @Test
    void getAccountThroughExceptionWhenAccountNotFound() {
        Mockito.when(accountMapper.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        ApiException apiException = Assertions.assertThrows(ApiException.class, () -> {
            accountService.getAccount(1L);
        });

        Assertions.assertEquals(HttpStatus.NOT_FOUND, apiException.getStatus());
    }

    @Test
    void getAccountReturnResponseWhenAccountIsFound() {
        Mockito.when(accountMapper.findById(Mockito.anyLong())).thenReturn(Optional.of(account));
        AccountResponse accountResponse = null;
        try {
            accountResponse = accountService.getAccount(1L);
        } catch (ApiException exception) {
            exception.printStackTrace();
        }

        Assertions.assertNotNull(accountResponse);
        Assertions.assertEquals(accountResponse.getCustomerId(), account.getCustomerId());
    }

    @Test
    void getAccountReturnBalanceAlsoWhenAccountIsFound() {
        account.setBalances(Arrays.asList(UnitTestHelper.getBalance()));
        Mockito.when(accountMapper.findById(Mockito.anyLong())).thenReturn(Optional.of(account));
        List<BalanceResponse> balanceResponses = null;
        try {
            AccountResponse accountResponse = accountService.getAccount(1L);
            balanceResponses = accountResponse.getBalances();
        } catch (ApiException exception) {
            exception.printStackTrace();
        }

        Assertions.assertNotNull(balanceResponses);
        Assertions.assertEquals(1, balanceResponses.size());
    }
}
