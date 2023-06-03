package com.tuum.assignment.controller;

import com.tuum.assignment.config.MessagePublisher;
import com.tuum.assignment.dto.AccountResponse;
import com.tuum.assignment.exception.ApiException;
import com.tuum.assignment.form.AccountForm;
import com.tuum.assignment.model.Account;
import com.tuum.assignment.service.AccountService;
import com.tuum.assignment.util.Utils;
import com.tuum.assignment.validator.AccountFormValidator;
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
