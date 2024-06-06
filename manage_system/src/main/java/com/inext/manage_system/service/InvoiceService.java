package com.inext.manage_system.service;

import java.util.List;

import com.inext.manage_system.dto.BaseRes;
import com.inext.manage_system.dto.CreateOrUpdateInvoiceReq;
import com.inext.manage_system.dto.InvoiceContentRes;
import com.inext.manage_system.dto.SearchInvoiceReq;
import com.inext.manage_system.dto.SearchInvoiceRes;
import com.inext.manage_system.model.Invoice;

public interface InvoiceService {

    // 採番取得
    public int getNewNumber();

    // 消費税リスト取得
    public List<Float> getTaxRateList();

    // 新規作成
    public BaseRes createInvoice(CreateOrUpdateInvoiceReq req);

    // 請求書更新
    public BaseRes updateInvoice(CreateOrUpdateInvoiceReq req);

    // 請求書検索
    public SearchInvoiceRes searchInvoice(SearchInvoiceReq req);

    // 請求書詳細確認
    public InvoiceContentRes checkInvoiceContent(String invoiceNo);

    // 請求書削除
    public BaseRes deleteInvoice(Invoice invoice);

}
