package com.banking.mapper;

import com.banking.model.Transaction;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Rana on 02 Jun, 2023
 */
@Mapper
public interface TransactionMapper {
    void insertTransaction(Transaction transaction);

    List<Transaction> findByAccountId(@Param("accountId") long accountId);
}
