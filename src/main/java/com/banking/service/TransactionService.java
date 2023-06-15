package com.banking.service;

import com.banking.dto.TransactionResponse;
import com.banking.model.Transaction;
import com.banking.exception.ApiException;

import java.util.List;

/**
 * Created by Rana on 27 May, 2023
 */
public interface TransactionService {
    TransactionResponse createTransaction(Transaction transaction) throws ApiException;

    List<Transaction> getTransactions(long accountId) throws ApiException;
}
