package com.banking.dto;

import com.banking.model.enums.Currency;
import com.banking.model.enums.Direction;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by Rana on 28 May, 2023
 */
@Data
@AllArgsConstructor
public class TransactionResponse {
    private long accountId;
    private long transactionId;
    private BigDecimal amount;
    private Currency currency;
    private Direction direction;
    private String description;
    private BigDecimal balanceAfterTransaction;
}
