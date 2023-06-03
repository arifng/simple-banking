package com.tuum.assignment.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;

/**
 * Created by Rana on 03 Jun, 2023
 */
@ExtendWith(SpringExtension.class)
public class UtilsTests {
    @Test
    void isEmptyReturnTrueWhenInputNull() {
        Assertions.assertTrue(Utils.isEmpty(null));
    }

    @Test
    void isEmptyReturnTrueWhenInputListEmpty() {
        Assertions.assertTrue(Utils.isEmpty(Arrays.asList()));
    }

    @Test
    void isEmptyReturnFalseWhenInputListHasValue() {
        Assertions.assertFalse(Utils.isEmpty(Arrays.asList("Item")));
    }
}
