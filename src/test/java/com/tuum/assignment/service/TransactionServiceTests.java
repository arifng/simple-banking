package com.tuum.assignment.service;

import com.tuum.assignment.UnitTestHelper;
import com.tuum.assignment.config.MessagePublisher;
import com.tuum.assignment.dto.TransactionResponse;
import com.tuum.assignment.exception.ApiException;
import com.tuum.assignment.mapper.AccountMapper;
import com.tuum.assignment.mapper.BalanceMapper;
import com.tuum.assignment.mapper.TransactionMapper;
import com.tuum.assignment.model.Account;
import com.tuum.assignment.model.Balance;
import com.tuum.assignment.model.Transaction;
import com.tuum.assignment.model.enums.Currency;
import com.tuum.assignment.model.enums.Direction;
import com.tuum.assignment.service.impl.BalanceServiceImpl;
import com.tuum.assignment.service.impl.TransactionServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Created by Rana on 03 Jun, 2023
 */
@ExtendWith(SpringExtension.class)
public class TransactionServiceTests {
    @MockBean
    private MessagePublisher publisher;
    @MockBean
    private BalanceService balanceService;
    @MockBean
    private AccountMapper accountMapper;
    @MockBean
    private TransactionMapper transactionMapper;

    private TransactionService transactionService;

    private Account account;
    private Balance balance;
    private Transaction transaction;

    @BeforeEach
    void setUp() {
        transactionService = new TransactionServiceImpl(publisher, balanceService, accountMapper, transactionMapper);
        account = UnitTestHelper.getAccount();
        balance = UnitTestHelper.getBalance();
        transaction = UnitTestHelper.getTransaction();
        try {
            Mockito.when(balanceService.updateAmountOfBalance(Mockito.any())).thenReturn(balance);
            Mockito.when(accountMapper.findById(Mockito.anyLong())).thenReturn(Optional.of(account));
        } catch (ApiException exception) {
            exception.printStackTrace();
        }
    }

    @Test
    void createTransactionWillCallInsertMethodOnMapper() {
        try {
            transactionService.createTransaction(transaction);
        } catch (ApiException exception) {
            exception.printStackTrace();
        }
        Mockito.verify(transactionMapper, Mockito.atLeastOnce()).insertTransaction(transaction);

    }

    @Test
    void createTransactionWillPublishAccountDetails() {
        try {
            Mockito.doNothing().when(transactionMapper).insertTransaction(transaction);
            transactionService.createTransaction(transaction);
        } catch (ApiException exception) {
            exception.printStackTrace();
        }

        Mockito.verify(publisher, Mockito.times(1)).publishAccountDetails(account);
    }

    @Test
    void createTransactionWillReturnResponse() {
        TransactionResponse response = null;
        try {
            Mockito.doNothing().when(transactionMapper).insertTransaction(transaction);
            Mockito.doNothing().when(publisher).publishAccountDetails(account);
            response = transactionService.createTransaction(transaction);
        } catch (ApiException exception) {
            exception.printStackTrace();
        }

        Assertions.assertNotNull(response);
    }

    @Test
    void getTransactionWillReturnListOfTransaction() {
        Mockito.when(transactionMapper.findByAccountId(Mockito.anyLong())).thenReturn(Arrays.asList(transaction));
        List<Transaction> transactions = null;
        try {
            transactions = transactionService.getTransactions(1);
        } catch (ApiException exception) {
            exception.printStackTrace();
        }


        Assertions.assertNotNull(transactions);
        Assertions.assertEquals(1, transactions.size());
    }
}
