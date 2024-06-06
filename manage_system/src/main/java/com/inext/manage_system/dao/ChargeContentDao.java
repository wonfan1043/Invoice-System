package com.inext.manage_system.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.inext.manage_system.model.ChargeContent;

@Mapper
public interface ChargeContentDao {
    
    //請求内容追加
    public void insertChargeContent(@Param("chargeContent") List<ChargeContent> chargeContent);

    //請求内容データ数取得 by invoiceNo
    public int countChargeContentNumByInvoiceNo(@Param("invoiceNo") String invoiceNo);

    //請求書内容取得 by invoiceNo
    public List<ChargeContent> selectAllChargeContentByInvoiceNo(@Param("invoiceNo") String invoiceNo);
    
    //請求内容削除 by invoiceNo
    public void deleteChargeContentByInvoiceNo(@Param("invoiceNo") String invoiceNo);

    //請求内容状態編集
    public void updateChargeContentStatusByInvoiceNo(@Param("chargeContent") ChargeContent chargeContent);

}
