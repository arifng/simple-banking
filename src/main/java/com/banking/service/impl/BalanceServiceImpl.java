package com.banking.service.impl;

import com.banking.model.Balance;
import com.banking.model.Transaction;
import com.banking.model.enums.Direction;
import com.banking.util.Utils;
import com.banking.exception.ApiException;
import com.banking.mapper.BalanceMapper;
import com.banking.service.BalanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

/**
 * Created by Rana on 29 May, 2023
 */
@Service
@RequiredArgsConstructor
@Transactional
public class BalanceServiceImpl implements BalanceService {
    private final BalanceMapper balanceMapper;

    @Transactional
    @Override
    public Balance updateAmountOfBalance(Transaction transaction) throws ApiException {
        List<Balance> balances = balanceMapper.findByAccountId(transaction.getAccountId());
        if (Utils.isEmpty(balances)) {
            throw new ApiException(HttpStatus.NOT_FOUND,
                    "Account is missing");
        }
        Balance balance = balances
                .stream()
                .filter(b -> Objects.equals(b.getCurrency(), transaction.getCurrency()))
                .findFirst()
                .orElse(null);

        if (balance == null) {
            throw new ApiException(HttpStatus.NOT_FOUND,
                    "Balance is missing");
        }

        BigDecimal calculatedAmount = balance.getAmount();
        if (Objects.equals(Direction.IN, transaction.getDirection())) {
            calculatedAmount = calculatedAmount.add(transaction.getAmount());
        } else {
            calculatedAmount = calculatedAmount.subtract(transaction.getAmount());
        }

        if (calculatedAmount.compareTo(BigDecimal.ZERO) < 0) {
            throw new ApiException(HttpStatus.NOT_ACCEPTABLE,
                    "Insufficient funds");
        }

        balance.setAmount(calculatedAmount);
        balanceMapper.updateAmount(balance);

        return balance;
    }
}
