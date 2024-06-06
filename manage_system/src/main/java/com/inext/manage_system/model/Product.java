package com.inext.manage_system.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    /** 商品ID **/
    private Integer itemId;

    /** カテゴリー **/
    private String category;

    /** サブカテゴリ **/
    private String subCategory;

    /** 商品名 **/
    private String itemName;

    /** 価額 **/
    private int originPrice;

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
