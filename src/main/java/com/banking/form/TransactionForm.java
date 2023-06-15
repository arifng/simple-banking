package com.banking.form;


import com.banking.model.enums.Currency;
import com.banking.model.enums.Direction;
import com.banking.model.Transaction;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Created by Rana on 27 May, 2023
 */
@Data
public class TransactionForm {
    @Min(value = 1, message = "Account missing")
    private long accountId;

    @Min(value = 1, message = "Invalid amount")
    private BigDecimal amount;

    @NotNull(message = "Currency is not provided")
    private Currency currency;

    @NotNull(message = "Direction is not provided")
    private Direction direction;

    @NotEmpty(message = "Description missing")
    private String description;

    public Transaction toModel() {
        Transaction transaction = new Transaction();
        transaction.setAccountId(getAccountId());
        transaction.setAmount(getAmount());
        transaction.setCurrency(getCurrency());
        transaction.setDirection(getDirection());
        transaction.setDescription(getDescription());
        return transaction;
    }
}
