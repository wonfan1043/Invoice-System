package com.inext.manage_system.dto;

import com.inext.manage_system.model.InvoiceSample;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceSampleSearchRes {

    /** 請求書サンプル **/
    private InvoiceSample sample;

    /** 結果メッセージコード **/
    private int code;
    
    /** 結果メッセージ**/
    private String message;

}
