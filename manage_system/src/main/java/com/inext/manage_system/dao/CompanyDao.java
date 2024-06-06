package com.inext.manage_system.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.inext.manage_system.model.Company;

@Mapper
public interface CompanyDao {

    // 会社リスト取得
    public List<Company> selectCorpList();

}
