package com.tuum.assignment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * Created by Rana on 27 May, 2023
 */
@Data
@AllArgsConstructor
public class AccountResponse {
    private long accountId;
    private String customerId;
    private List<BalanceResponse> balances;
}
