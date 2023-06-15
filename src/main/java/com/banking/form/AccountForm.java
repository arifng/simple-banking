package com.banking.form;


import com.banking.util.Utils;
import com.banking.model.enums.Currency;
import com.banking.model.Account;
import com.banking.model.Balance;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Rana on 27 May, 2023
 */
@Data
public class AccountForm {
    @NotEmpty(message = "Customer ID is not provided")
    String customerId;
    @NotEmpty(message = "Country is not provided")
    String country;
    @NotEmpty(message = "No currency is provided")
    List<Currency> currencies;

    public Account toModel() {
        Account account = new Account();
        account.setCountry(getCountry());
        account.setCustomerId(getCustomerId());
        account.setBalances(populateBalances());
        return account;
    }

    private List<Balance> populateBalances() {
        return Utils.isEmpty(getCurrencies()) ? Collections.emptyList() :
                getCurrencies()
                        .stream()
                        .map(currency -> {
                            Balance b = new Balance();
                            b.setCurrency(currency);
                            return b; })
                        .collect(Collectors.toList());
    }
}
