package com.banking.model.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Rana on 28 May, 2023
 */
@Getter
public enum Currency {
    EUR,
    SEK,
    GBP,
    USD;

    public static Set<Currency> currencySet() {
        return Arrays.stream(Currency.values())
                .collect(Collectors.toSet());
    }
}
