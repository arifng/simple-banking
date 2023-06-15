package com.banking.validator;

import com.banking.form.AccountForm;
import com.banking.model.enums.Currency;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by Rana on 27 May, 2023
 */
@Component
public class AccountFormValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return AccountForm.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        AccountForm form = (AccountForm) target;
        validateCurrencies(form, errors);
    }

    private void validateCurrencies(AccountForm form, Errors errors) {
        if (form.getCurrencies() != null &&
                !form.getCurrencies().isEmpty() &&
                !Currency.currencySet().containsAll(form.getCurrencies())) {
            errors.rejectValue("currencies", "", "Invalid currency");
        }
    }
}
