package com.inext.manage_system.dto;

import java.util.List;

import com.inext.manage_system.model.ChargeContent;
import com.inext.manage_system.model.Invoice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrUpdateInvoiceReq {

    /** 作成/編集する請求書 **/
    private Invoice invoice;
    
    /** 作成/編集する請求書の請求内容 **/
    private List<ChargeContent> products;

}
