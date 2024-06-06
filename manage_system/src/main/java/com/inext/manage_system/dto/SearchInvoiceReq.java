package com.inext.manage_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchInvoiceReq {

    /** ページ番号 **/
    private Integer pageNum;

    /** 日付タイプ **/
    private int dateType;

    /** 年 **/
    private int year;

    /** 月 **/
    private int month;

    /** 協力会社名 **/
    private String corpName;

}
