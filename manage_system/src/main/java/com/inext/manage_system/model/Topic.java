package com.inext.manage_system.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Topic {

    /** 主旨ID **/
    private String topicId;

    /** 主旨 **/
    private String topicName;

    /** コメント **/
    private String comment;

    /** 作成者 **/
    private String creater;

    /** 作成時間 **/
    private LocalDateTime createDatetime;

    /** 編集者 **/
    private String updater;

    /** 編集時間 **/
    private LocalDateTime updateDatetime;

    /** 削除フラグ **/
    private boolean delete;

}
