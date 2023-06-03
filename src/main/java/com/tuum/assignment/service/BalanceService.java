package com.tuum.assignment.service;

import com.tuum.assignment.exception.ApiException;
import com.tuum.assignment.model.Balance;
import com.tuum.assignment.model.Transaction;

/**
 * Created by Rana on 29 May, 2023
 */
public interface BalanceService {
    Balance updateAmountOfBalance(Transaction transaction) throws ApiException;
}
