package com.tuum.assignment.integrationtests;

import com.tuum.assignment.dto.AccountResponse;
import com.tuum.assignment.exception.ApiException;
import com.tuum.assignment.model.Account;
import com.tuum.assignment.model.Balance;
import com.tuum.assignment.model.enums.Currency;
import com.tuum.assignment.service.AccountService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Arrays;

/**
 * Created by Rana on 03 Jun, 2023
 */
@SpringBootTest
public class AccountServiceTests {
    @Autowired
    private AccountService accountService;

    private Account populateAccount() {
        Account account = new Account();
        account.setCountry("Bd");
        account.setCustomerId("C456");

        Balance balance1 = new Balance();
        balance1.setAmount(BigDecimal.ZERO);
        balance1.setCurrency(Currency.USD);

        Balance balance2 = new Balance();
        balance2.setAmount(BigDecimal.ZERO);
        balance2.setCurrency(Currency.USD);

        account.setBalances(Arrays.asList(balance1, balance2));
        return account;
    }

    @Test
    void createAccountSuccessfullyWhenAllValuesAreCorrect() {
        Account account = populateAccount();
        try {
            AccountResponse accountResponse = accountService.createAccount(account);

            Assertions.assertNotNull(accountResponse);
            Assertions.assertEquals(account.getCustomerId(), accountResponse.getCustomerId());
            Assertions.assertEquals(2, accountResponse.getBalances().size());

            Balance balance = account.getBalances().get(0);
            Assertions.assertEquals(balance.getCurrency(), accountResponse.getBalances().get(0).getCurrency());


        } catch (ApiException exception) {
            exception.printStackTrace();
        }
    }



}
