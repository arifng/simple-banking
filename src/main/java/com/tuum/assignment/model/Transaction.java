package com.tuum.assignment.model;

import com.tuum.assignment.model.enums.Currency;
import com.tuum.assignment.model.enums.Direction;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by Rana on 02 Jun, 2023
 */
@Data
public class Transaction {
    private long id;
    private long accountId;
    private Currency currency;
    private Direction direction;
    private String description;
    private BigDecimal amount;
}
