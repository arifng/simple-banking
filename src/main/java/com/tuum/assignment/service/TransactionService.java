package com.tuum.assignment.service;

import com.tuum.assignment.exception.ApiException;
import com.tuum.assignment.dto.TransactionResponse;
import com.tuum.assignment.model.Transaction;

import java.util.List;

/**
 * Created by Rana on 27 May, 2023
 */
public interface TransactionService {
    TransactionResponse createTransaction(Transaction transaction) throws ApiException;

    List<Transaction> getTransactions(long accountId) throws ApiException;
}
