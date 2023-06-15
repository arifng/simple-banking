package com.banking;

import com.banking.model.Account;
import com.banking.model.Balance;
import com.banking.model.Transaction;
import com.banking.model.enums.Currency;
import com.banking.model.enums.Direction;

import java.math.BigDecimal;
import java.util.Collections;

/**
 * Created by Rana on 03 Jun, 2023
 */
public class UnitTestHelper {
    public static Account getAccount() {
        Account account = new Account();
        account.setId(1L);
        account.setCustomerId("customer");
        account.setCountry("BD");
        account.setBalances(Collections.emptyList());
        return account;
    }

    public static Transaction getTransaction() {
        Transaction transaction = new Transaction();
        transaction.setId(1);
        transaction.setAccountId(1);
        transaction.setAmount(BigDecimal.ZERO);
        transaction.setDirection(Direction.IN);
        transaction.setCurrency(Currency.USD);
        transaction.setDescription("");
        return transaction;
    }

    public static Balance getBalance() {
        Balance balance = new Balance();
        balance.setCurrency(Currency.USD);
        balance.setAmount(BigDecimal.valueOf(5));
        balance.setAccountId(5);
        return balance;
    }
}
