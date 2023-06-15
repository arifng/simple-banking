package com.banking.service;

import com.banking.model.Balance;
import com.banking.model.Transaction;
import com.banking.exception.ApiException;

/**
 * Created by Rana on 29 May, 2023
 */
public interface BalanceService {
    Balance updateAmountOfBalance(Transaction transaction) throws ApiException;
}
