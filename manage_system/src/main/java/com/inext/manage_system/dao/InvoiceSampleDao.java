package com.inext.manage_system.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.inext.manage_system.model.InvoiceSample;

@Mapper
public interface InvoiceSampleDao {

    // サンプルリスト取得
    public List<InvoiceSample> selectSampleList();

    // サンプル取得 by corpName
    public InvoiceSample selectInvoiceSampleByCorpName(@Param("corpName") String corpName);

    // サンプル追加
    public void insertInvoiceSample(@Param("invoiceSample") InvoiceSample invoiceSample);

    // サンプル編集
    public void updateInvoiceSampleByCorpName(@Param("invoiceSample") InvoiceSample invoiceSample);

    // サンプル状態編集
    public void updateInvoiceSampleStatusByCorpName(@Param("invoiceSample") InvoiceSample invoiceSample);

}
