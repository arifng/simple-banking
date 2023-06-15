package com.banking.util;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindingResult;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

/**
 * Created by Rana on 27 May, 2023
 */
public class Utils {
    public static String getErrorMessage(BindingResult result) {
        return result.getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining("\n"));
    }

    public static <E> boolean isEmpty(Collection<E> items) {
        return items == null || items.isEmpty();
    }
}
