package com.inext.manage_system.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.inext.manage_system.model.Bank;

@Mapper
public interface BankDao {

    // 口座取得 by bankId
    public Bank selectBankAccountByBankId(@Param("bankId") int bankId);

    // 口座リスト取得
    public List<Bank> selectBankList();

}
