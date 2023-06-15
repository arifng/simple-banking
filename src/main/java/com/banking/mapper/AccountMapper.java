package com.banking.mapper;

import com.banking.model.Account;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

/**
 * Created by Rana on 01 Jun, 2023
 */
@Mapper
public interface AccountMapper {
    void insertAccount(Account account);

    Optional<Account> findById(@Param("id") long accountId);
}
