package com.inext.manage_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceContentReq {

    /** 請求書番号 **/
    private String invoiceNo;

    /** 主旨ID **/
    private String topicId;

    /** 協力会社ID **/
    private int corpId;
    
}
