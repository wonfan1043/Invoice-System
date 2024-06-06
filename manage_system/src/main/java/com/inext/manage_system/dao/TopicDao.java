package com.inext.manage_system.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.inext.manage_system.dto.SearchTopicReq;
import com.inext.manage_system.model.Topic;

@Mapper
public interface TopicDao {
    
    // 主旨リスト取得
    public List<Topic> selectTopicDistinct();

    // 主旨取得 by topicId
    public Topic selectTopicByTopicId(@Param("topicId") String topicId);

    // 主旨取得 by topicName
    public String selectTopicNameByTopicName(@Param("topicName") String topicName);

    // 主旨追加
    public void insertTopic(@Param("topic") Topic topic);

    // 主旨編集 by topicId
    public void updateTopicByTopicId(@Param("topic") Topic topic);

    // 主旨取得 by topicId, topicName, createDatetime
    public List<Topic> selectTopicByTopicNameCreateDatetime(@Param("searchReq") SearchTopicReq searchReq);

    // 主旨状態編集
    public void updateTopiceStatusByTopicId(@Param("topic") Topic topic);

}
