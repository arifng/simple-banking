package com.banking.controller;

import com.banking.dto.AccountResponse;
import com.banking.exception.ApiException;
import com.banking.form.AccountForm;
import com.banking.service.AccountService;
import com.banking.validator.AccountFormValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.validation.BindingResult;

/**
 * Created by Rana on 03 Jun, 2023
 */
@ExtendWith(SpringExtension.class)
public class AccountControllerTests {
    @MockBean
    private AccountFormValidator validator;
    @MockBean
    private AccountService accountService;
    @MockBean
    private BindingResult bindingResult;

    private AccountController controller;
    private AccountForm form;
    private AccountResponse response;

    @BeforeEach
    void setUp() {
        controller = new AccountController(validator, accountService);
        form = new AccountForm();
        Mockito.when(bindingResult.hasErrors()).thenReturn(false);
        Mockito.doNothing().when(validator).validate(Mockito.any(), Mockito.any());
        response = new AccountResponse(0, null, null);
        try {
            Mockito.when(accountService.createAccount(Mockito.any()))
                    .thenReturn(response);
        } catch (ApiException exception) {
            exception.printStackTrace();
        }
    }

    @Test
    void createAccountWillReturnResponse() {
        AccountResponse response = null;
        try {
            response = controller.createAccount(form, bindingResult);
        } catch (ApiException exception) {
            exception.printStackTrace();
        }

        Assertions.assertNotNull(response);
    }

    @Test
    void createAccountWillThrowExceptionIfFormHasProblem() {
        Mockito.when(bindingResult.hasErrors()).thenReturn(true);
        ApiException apiException = Assertions.assertThrows(ApiException.class, () -> {
            controller.createAccount(form, bindingResult);
        });

        Assertions.assertNotNull(apiException);
    }

    @Test
    void getAccountWillReturnResponse() {
        AccountResponse accountResponse = null;

        try {
            Mockito.when(accountService.getAccount(Mockito.anyLong())).thenReturn(response);
            accountResponse = controller.getAccount(1);
        } catch (ApiException exception) {
            exception.printStackTrace();
        }

        Assertions.assertNotNull(accountResponse);
    }
}
