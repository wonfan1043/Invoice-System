package com.inext.manage_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchTopicReq {

    /** ページ番号 **/
    private Integer pageNum;

    /** 年 **/
    private int year;

    /** 月 **/
    private int month;

    /** キーワード **/
    private String keyword;

}
