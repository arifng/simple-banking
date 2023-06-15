package com.banking.service;

import com.banking.dto.AccountResponse;
import com.banking.model.Account;
import com.banking.exception.ApiException;

/**
 * Created by Rana on 27 May, 2023
 */
public interface AccountService {
    AccountResponse createAccount(Account account) throws ApiException;

    AccountResponse getAccount(long accountId) throws ApiException;
}
