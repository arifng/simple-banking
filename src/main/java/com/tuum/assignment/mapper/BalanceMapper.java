package com.tuum.assignment.mapper;

import com.tuum.assignment.model.Balance;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Rana on 02 Jun, 2023
 */
@Mapper
public interface BalanceMapper {
    void insertBalances(List<Balance> balances);

    List<Balance> findByAccountId(@Param("accountId") long accountId);

    void updateAmount(Balance balance);
}
