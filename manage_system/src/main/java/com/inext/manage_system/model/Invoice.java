package com.inext.manage_system.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Invoice {

    /** 請求書番号 **/
    private String invoiceNo;
    
    /** 会社名 **/
    private String corpName;
    
    /** 発送先 **/
    private String receiver;

    /** 発送先郵便番号 **/
    private String postcode;

    /** 発送先都度府県 **/
    private String county;

    /** 発送先市区町村 **/
    private String town;

    /** 発送先住所 **/
    private String address;

    /** 発送先ビル名 **/
    private String building;

    /** 主旨 **/
    private String topicName;

    /** 銀行ID **/
    private Integer bankId;

    /** 税率 **/
    private Float tax;

    /** メモ **/
    private String memo;

    /** 決済期限 **/
    private LocalDate dueDate;

    /** 請求日 **/
    private LocalDate chargeDate;

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
