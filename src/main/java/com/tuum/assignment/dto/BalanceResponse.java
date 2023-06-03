package com.tuum.assignment.dto;

import com.tuum.assignment.model.enums.Currency;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by Rana on 27 May, 2023
 */
@Data
@AllArgsConstructor
public class BalanceResponse {
     private Currency currency;
     private BigDecimal amount;
}
