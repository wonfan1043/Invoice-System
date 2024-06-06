package com.inext.manage_system.dto;

import com.github.pagehelper.PageInfo;
import com.inext.manage_system.model.Topic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchTopicRes {

    /** 主旨リスト **/
    private PageInfo<Topic> topicList;

    /** 結果メッセージのコード **/
    private int code;

    /** 結果メッセージ **/
    private String message;
    
}
