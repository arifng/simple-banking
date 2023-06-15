package com.banking.model.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Rana on 28 May, 2023
 */
@Getter
public enum Direction {
    IN,
    OUT;

    public static Set<Direction> directionSet() {
        return Arrays.stream(Direction.values())
                .collect(Collectors.toSet());
    }
}
