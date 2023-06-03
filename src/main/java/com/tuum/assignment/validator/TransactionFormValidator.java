package com.tuum.assignment.validator;

import com.tuum.assignment.model.enums.Currency;
import com.tuum.assignment.model.enums.Direction;
import com.tuum.assignment.exception.ApiException;
import com.tuum.assignment.form.TransactionForm;
import com.tuum.assignment.service.AccountService;
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
