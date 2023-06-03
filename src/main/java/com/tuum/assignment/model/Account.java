package com.tuum.assignment.model;

import lombok.Data;

import java.util.List;

/**
 * Created by Rana on 27 May, 2023
 */
@Data
public class Account {
        private long id;
        private String customerId;
        private String country;
        private List<Balance> balances;
}
