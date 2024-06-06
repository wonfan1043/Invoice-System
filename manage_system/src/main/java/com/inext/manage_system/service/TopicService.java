package com.inext.manage_system.service;

import java.util.List;

import com.inext.manage_system.dto.BaseRes;
import com.inext.manage_system.dto.SearchTopicReq;
import com.inext.manage_system.dto.SearchTopicRes;
import com.inext.manage_system.dto.TopicContentRes;
import com.inext.manage_system.model.Topic;

public interface TopicService {

    // 主旨リスト取得
    public List<Topic> getTopicList();

    // 主旨作成
    public BaseRes createTopic(Topic topic);

    // 主旨編集
    public BaseRes updateTopic(Topic topic);

    // 主旨検索
    public SearchTopicRes searchTopic(SearchTopicReq searchTopicReq);

    // 主旨詳細チェック
    public TopicContentRes checkTopicContent(String topicId);

    // 主旨削除
    public BaseRes deleteTopic(Topic topic);

}
