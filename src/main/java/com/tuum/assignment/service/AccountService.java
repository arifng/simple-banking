package com.tuum.assignment.service;

import com.tuum.assignment.dto.AccountResponse;
import com.tuum.assignment.exception.ApiException;
import com.tuum.assignment.model.Account;

/**
 * Created by Rana on 27 May, 2023
 */
public interface AccountService {
    AccountResponse createAccount(Account account) throws ApiException;

    AccountResponse getAccount(long accountId) throws ApiException;
}
