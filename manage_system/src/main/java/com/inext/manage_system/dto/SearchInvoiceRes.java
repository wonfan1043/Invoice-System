package com.inext.manage_system.dto;

import java.util.List;

import com.inext.manage_system.model.Invoice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchInvoiceRes {

    /** 主旨リスト **/
    private List<Invoice> invoiceList;

    /** 結果メッセージのコード **/
    private int code;

    /** 結果メッセージ **/
    private String message;

}
