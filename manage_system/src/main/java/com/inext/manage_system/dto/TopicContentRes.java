package com.inext.manage_system.dto;

import com.inext.manage_system.model.Topic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopicContentRes {

    /** 主旨詳細 **/
    private Topic topic;

    /** 結果メッセージのコード **/
    private int code;

    /** 結果メッセージ **/
    private String message;

}
