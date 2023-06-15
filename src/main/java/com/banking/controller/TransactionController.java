package com.banking.controller;

import com.banking.dto.TransactionResponse;
import com.banking.form.TransactionForm;
import com.banking.model.Transaction;
import com.banking.service.TransactionService;
import com.banking.util.Utils;
import com.banking.exception.ApiException;
import com.banking.validator.TransactionFormValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by Rana on 27 May, 2023
 */
@RestController
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionFormValidator validator;
    private final TransactionService transactionService;

    @PostMapping("/api/transaction/create")
    public TransactionResponse createTransaction(@Valid @RequestBody TransactionForm form,
                                                 BindingResult bindingResult)
            throws ApiException {
        System.out.println("TRNX requested");
        validator.validate(form, bindingResult);
        if (bindingResult.hasErrors()) {
            throw new ApiException(HttpStatus.NOT_ACCEPTABLE,
                    Utils.getErrorMessage(bindingResult));
        }

        return transactionService.createTransaction(form.toModel());
    }

    @GetMapping("/api/transaction/{accountId}")
    public List<Transaction> getTransactions(@PathVariable("accountId") long accountId) throws ApiException {
        return transactionService.getTransactions(accountId);
    }
}
