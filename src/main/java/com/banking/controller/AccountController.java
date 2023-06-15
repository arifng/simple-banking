package com.banking.controller;

import com.banking.dto.AccountResponse;
import com.banking.form.AccountForm;
import com.banking.service.AccountService;
import com.banking.util.Utils;
import com.banking.exception.ApiException;
import com.banking.validator.AccountFormValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by Rana on 27 May, 2023
 */
@RestController
@RequiredArgsConstructor
public class AccountController {
    private final AccountFormValidator validator;
    private final AccountService accountService;

    @PostMapping("/api/account/create")
    public AccountResponse createAccount(@Valid @RequestBody AccountForm form,
                                         BindingResult bindingResult)
            throws ApiException {

        validator.validate(form, bindingResult);
        if (bindingResult.hasErrors()) {
            throw new ApiException(HttpStatus.NOT_ACCEPTABLE,
                    Utils.getErrorMessage(bindingResult));
        }

        return accountService.createAccount(form.toModel());
    }

    @GetMapping("/api/account/{accountId}")
    public AccountResponse getAccount(@PathVariable("accountId") long accountId) throws ApiException {
        System.out.println("requested");
        return accountService.getAccount(accountId);
    }
}
