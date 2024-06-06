package com.inext.manage_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import com.inext.manage_system.enums.CommonMessage;
import com.inext.manage_system.model.Bank;
import com.inext.manage_system.model.ChargeContent;
import com.inext.manage_system.model.Invoice;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceContentRes {

    /** 主旨モデル **/
    private Invoice invoice;

    /** 銀行口座 **/
    private Bank bank;

    /** 請求内容 **/
    private List<ChargeContent> product;

    /** 結果メッセージ **/
    private CommonMessage message;

}
