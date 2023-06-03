package com.tuum.assignment.service;

import com.tuum.assignment.UnitTestHelper;
import com.tuum.assignment.dto.AccountResponse;
import com.tuum.assignment.exception.ApiException;
import com.tuum.assignment.mapper.BalanceMapper;
import com.tuum.assignment.model.Account;
import com.tuum.assignment.model.Balance;
import com.tuum.assignment.model.Transaction;
import com.tuum.assignment.model.enums.Currency;
import com.tuum.assignment.model.enums.Direction;
import com.tuum.assignment.service.impl.BalanceServiceImpl;
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
public class BalanceServiceTests {

    @MockBean
    private BalanceMapper balanceMapper;

    private BalanceService balanceService;

    @BeforeEach
    void setUp() {
        balanceService = new BalanceServiceImpl(balanceMapper);
        Mockito.when(balanceMapper.findByAccountId(Mockito.anyLong())).thenReturn(balances());
    }

    private List<Balance> balances() {
        Balance balance1 = new Balance();
        balance1.setCurrency(Currency.USD);
        balance1.setAmount(BigDecimal.ZERO);
        balance1.setAccountId(5);

        Balance balance2 = new Balance();
        balance2.setCurrency(Currency.EUR);
        balance2.setAmount(BigDecimal.ZERO);
        balance2.setAccountId(5);

        return Arrays.asList(balance1, balance2);
    }

    @Test
    void updateBalanceWillThrowExceptionWhenBalanceListEmpty() {
        Mockito.when(balanceMapper.findByAccountId(Mockito.anyLong())).thenReturn(Collections.emptyList());
        ApiException apiException = Assertions.assertThrows(ApiException.class, () -> {
            balanceService.updateAmountOfBalance(UnitTestHelper.getTransaction());
        });

        Assertions.assertNotNull(apiException);
    }

    @Test
    void updateBalanceWilThrowExceptionIfBalanceNotFoundForCurrency() {
        Transaction transaction = UnitTestHelper.getTransaction();
        transaction.setCurrency(Currency.SEK);

        ApiException apiException = Assertions.assertThrows(ApiException.class, () -> {
            balanceService.updateAmountOfBalance(transaction);
        });

        Assertions.assertNotNull(apiException);
    }

    @Test
    void updateBalanceWillAddAmountForInDirection() {
        BigDecimal targetAmount = BigDecimal.valueOf(10);
        Transaction transaction = UnitTestHelper.getTransaction();
        transaction.setAmount(targetAmount);

        Balance balance = null;
        try {
            balance = balanceService.updateAmountOfBalance(transaction);
        } catch (ApiException exception) {
            exception.printStackTrace();
        }

        Assertions.assertNotNull(balance);
        Assertions.assertEquals(0, balance.getAmount().compareTo(targetAmount));
    }

    @Test
    void updateBalanceThrowExceptionOutDirectionWhenAmountBecomeNegative() {
        Transaction transaction = UnitTestHelper.getTransaction();
        transaction.setDirection(Direction.OUT);
        transaction.setAmount(BigDecimal.valueOf(10));

        ApiException apiException = Assertions.assertThrows(ApiException.class, () -> {
            balanceService.updateAmountOfBalance(transaction);
        });

        Assertions.assertNotNull(apiException);
    }
}
