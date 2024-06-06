package com.inext.manage_system.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface InvoiceNoDao {

    // 最後の番号を取得
    public int selectInvoiceNoByOrder();

    // 新番号追加
    public void insertInvoiceNo(@Param("type") String type);

}