package com.banking.controller;

import com.banking.UnitTestHelper;
import com.banking.dto.TransactionResponse;
import com.banking.exception.ApiException;
import com.banking.form.TransactionForm;
import com.banking.model.Transaction;
import com.banking.model.enums.Currency;
import com.banking.model.enums.Direction;
import com.banking.service.TransactionService;
import com.banking.validator.TransactionFormValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.validation.BindingResult;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Rana on 03 Jun, 2023
 */
@ExtendWith(SpringExtension.class)
public class TransactionControllerTests {
    @MockBean
    private TransactionFormValidator validator;
    @MockBean
    private TransactionService transactionService;
    @MockBean
    private BindingResult bindingResult;

    private TransactionController controller;
    private TransactionForm form;
    private TransactionResponse response;

    @BeforeEach
    void setUp() {
        controller = new TransactionController(validator, transactionService);
        form = new TransactionForm();
        Mockito.when(bindingResult.hasErrors()).thenReturn(false);
        Mockito.doNothing().when(validator).validate(Mockito.any(), Mockito.any());
        response = new TransactionResponse(0, 0,
                BigDecimal.ZERO, Currency.USD, Direction.IN, "", BigDecimal.ZERO);
        try {
            Mockito.when(transactionService.createTransaction(Mockito.any()))
                    .thenReturn(response);
        } catch (ApiException exception) {
            exception.printStackTrace();
        }
    }

    @Test
    void createTransactionWillReturnResponse() {
        TransactionResponse response = null;
        try {
            response = controller.createTransaction(form, bindingResult);
        } catch (ApiException exception) {
            exception.printStackTrace();
        }

        Assertions.assertNotNull(response);
    }

    @Test
    void createTransactionWillThrowExceptionIfFormHasProblem() {
        Mockito.when(bindingResult.hasErrors()).thenReturn(true);
        ApiException apiException = Assertions.assertThrows(ApiException.class, () -> {
            controller.createTransaction(form, bindingResult);
        });

        Assertions.assertNotNull(apiException);
    }

    @Test
    void getTransactionWillReturnResponse() {
        List<Transaction> transactions = null;

        try {
            Mockito.when(transactionService.getTransactions(Mockito.anyLong()))
                    .thenReturn(Arrays.asList(UnitTestHelper.getTransaction()));
            transactions = controller.getTransactions(1);
        } catch (ApiException exception) {
            exception.printStackTrace();
        }

        Assertions.assertNotNull(transactions);
        Assertions.assertEquals(1, transactions.size());
    }
}
