package com.inext.manage_system.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChargeContent {

    /** 請求書番号 **/
    private String invoiceNo;

    /** 商品名 **/
    private String itemName;

    /** 数量 **/
    private int quantity;

    /** 単価 **/
    private long unitPrice;

    /** 作成者 **/
    private String creator;

    /** 作成時間 **/
    private LocalDateTime createDatetime;

    /** 編集者 **/
    private String updater;

    /** 編集時間 **/
    private LocalDateTime updateDatetime;

    /** 削除フラグ **/
    private boolean delete;

}
