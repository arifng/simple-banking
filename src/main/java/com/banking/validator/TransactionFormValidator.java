package com.banking.validator;

import com.banking.form.TransactionForm;
import com.banking.model.enums.Currency;
import com.banking.model.enums.Direction;
import com.banking.service.AccountService;
import com.banking.exception.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by Rana on 27 May, 2023
 */
@Component
@RequiredArgsConstructor
public class TransactionFormValidator implements Validator {

    private final AccountService accountService;

    @Override
    public boolean supports(Class<?> clazz) {
        return TransactionForm.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        TransactionForm form = (TransactionForm) target;
        validateTransaction(form, errors);
        validateIfAccountExists(form, errors);
    }

    private void validateTransaction(TransactionForm form, Errors errors) {
        if (!Currency.currencySet().contains(form.getCurrency())) {
            errors.rejectValue("currency", "", "Invalid currency");
        }

        if (!Direction.directionSet().contains(form.getDirection())) {
            errors.rejectValue("direction", "", "Invalid direction");
        }
    }

    private void validateIfAccountExists(TransactionForm form, Errors errors) {
        try {
            accountService.getAccount(form.getAccountId());
        } catch (ApiException exception) {
            errors.rejectValue("accountId", "", "Account missing");
        }
    }
}
