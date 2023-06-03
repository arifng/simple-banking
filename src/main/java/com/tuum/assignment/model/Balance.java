package com.tuum.assignment.model;

import com.tuum.assignment.model.enums.Currency;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Created by Rana on 27 May, 2023
 */
@Getter
@Setter
@EqualsAndHashCode
public class Balance {
    private long id;
    private long accountId;
    private BigDecimal amount;
    private Currency currency;
}
