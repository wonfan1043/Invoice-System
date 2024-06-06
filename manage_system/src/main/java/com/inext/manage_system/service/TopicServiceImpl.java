package com.inext.manage_system.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.inext.manage_system.dao.TopicDao;
import com.inext.manage_system.enums.AppConstants;
import com.inext.manage_system.enums.CommonMessage;
import com.inext.manage_system.model.Topic;
import com.inext.manage_system.dto.BaseRes;
import com.inext.manage_system.dto.SearchTopicReq;
import com.inext.manage_system.dto.SearchTopicRes;
import com.inext.manage_system.dto.TopicContentRes;

@Service
public class TopicServiceImpl implements TopicService {

    @Autowired
    private TopicDao topicDao;

    /**
     * 主旨リスト取得
     * 
     * @return 主旨リスト
     */
    @Override
    public List<Topic> getTopicList(){
        return topicDao.selectTopicDistinct();
    }

    /**
     * 主旨作成
     * 
     * @param topic 主旨モデル
     * @return 結果メッセージオブジェクト
     */
    @Override
    public BaseRes createTopic(Topic topic) {
        // 入力チェック
        if (checkParam(topic, true) == false) {
            return new BaseRes(CommonMessage.PARAM_ERROR.getCode(), CommonMessage.PARAM_ERROR.getMessage());
        }
        // 桁数チェック
        if (checkByte(topic, true) == false) {
            return new BaseRes(CommonMessage.DATA_TOO_BIG.getCode(), CommonMessage.DATA_TOO_BIG.getMessage());
        }
        // DBに重複の主旨がないことをチェックする
        if (checkExist(topic, true) == false) {
            return new BaseRes(CommonMessage.TOPIC_ALREADY_EXISTS.getCode(), CommonMessage.TOPIC_ALREADY_EXISTS.getMessage());
        }
        // 作成時間を現時点にする
        topic.setCreateDatetime(LocalDateTime.now());
        // 主旨を追加する
        topicDao.insertTopic(topic);
        return new BaseRes(CommonMessage.CREATE_SUCCESS.getCode(), CommonMessage.CREATE_SUCCESS.getMessage());
    }

    /**
     * 主旨編集
     * 
     * @param topic 主旨モデル
     * @return 結果メッセージオブジェクト
     */
    @Override
    public BaseRes updateTopic(Topic topic) {
        // 入力チェック
        if (checkParam(topic, false) == false) {
            return new BaseRes(CommonMessage.PARAM_ERROR.getCode(), CommonMessage.PARAM_ERROR.getMessage());
        }
        // 桁数チェック
        if (checkByte(topic, false) == false) {
            return new BaseRes(CommonMessage.DATA_TOO_BIG.getCode(), CommonMessage.DATA_TOO_BIG.getMessage());
        }
        // DBに主旨があることをチェックする
        if (checkExist(topic, false) == false) {
            return new BaseRes(CommonMessage.TOPIC_NOT_FOUND.getCode(), CommonMessage.TOPIC_NOT_FOUND.getMessage());
        }
        // 編集時間を現時点にする
        topic.setUpdateDatetime(LocalDateTime.now());
        // 主旨を編集する
        topicDao.updateTopicByTopicId(topic);
        return new BaseRes(CommonMessage.UPDATE_SUCCESS.getCode(), CommonMessage.UPDATE_SUCCESS.getMessage());
    }

    /**
     * 主旨検索
     * 
     * @param searchTopicReq 検索条件オブジェクト
     * @return 主旨リストと結果メッセージ
     */
    @Override
    public SearchTopicRes searchTopic(SearchTopicReq searchTopicReq) {
        // 検索条件：年と月のチェック
        // (1)現時点の年と月を宣言する
        int currentYearAndMonth = (LocalDate.now().getYear() * 100) + LocalDate.now().getMonthValue();
        // (2)検索条件の年と月を宣言する
        int searchYearAndMonth = (searchTopicReq.getYear() * 100) + searchTopicReq.getMonth();
        // (3)検索条件の年と月は現時点より遅くないことをチェックする
        if (currentYearAndMonth < searchYearAndMonth){
            return new SearchTopicRes(null, CommonMessage.INVALID_TIME_RANGE.getCode(), 
                            CommonMessage.INVALID_TIME_RANGE.getMessage());
        }
        // ページ番号を設定する
        Integer finalPageNumber = searchTopicReq.getPageNum() != null ? searchTopicReq.getPageNum() : AppConstants.DEFAULT_PAGE_NUM;
        // 主旨リストを取得する
        PageHelper.startPage(finalPageNumber, AppConstants.PAGE_SIZE);
        List<Topic> topicList = topicDao.selectTopicByTopicNameCreateDatetime(searchTopicReq);
        // topicListがヌルの場合は請求書見つからなかったメッセージを返す
        if (topicList.isEmpty()) {
            return new SearchTopicRes(new PageInfo<>(topicList), CommonMessage.TOPIC_NOT_FOUND.getCode(), 
                            CommonMessage.TOPIC_NOT_FOUND.getMessage());
        }
        return new SearchTopicRes(new PageInfo<>(topicList), CommonMessage.SUCCESS.getCode(),
                            CommonMessage.SUCCESS.getMessage());
    }

    /**
     * 主旨詳細チェック
     * 
     * @param topicId 主旨ID
     * @return 主旨詳細と結果メッセージ
     */
    @Override
    public TopicContentRes checkTopicContent(String topicId) {
        // 入力チェック：主旨IDがヌル、空文字列、空白文字列ではないこと
        if (!StringUtils.hasText(topicId)) {
            return new TopicContentRes(null, CommonMessage.PARAM_ERROR.getCode(),
                            CommonMessage.PARAM_ERROR.getMessage());
        }
        // 主旨詳細を取得する
        Topic selectedTopic = topicDao.selectTopicByTopicId(topicId);
        // 主旨見つからない場合はメッセージを返す
        if (selectedTopic == null) {
            return new TopicContentRes(selectedTopic, CommonMessage.TOPIC_NOT_FOUND.getCode(),
                            CommonMessage.TOPIC_NOT_FOUND.getMessage());
        }
        return new TopicContentRes(selectedTopic, CommonMessage.SUCCESS.getCode(), 
                            CommonMessage.SUCCESS.getMessage());
    }

    /**
     * 主旨削除
     * 
     * @param topic 主旨モデル
     * @return 結果メッセージオブジェクト
     */
    @Override
    public BaseRes deleteTopic(Topic topic) {
        // 入力チェック：主旨IDと編集者がヌル、空文字列、空白文字列ではないこと
        if (!StringUtils.hasText(topic.getTopicId()) || !StringUtils.hasText(topic.getUpdater())) {
            return new BaseRes(CommonMessage.PARAM_ERROR.getCode(), CommonMessage.PARAM_ERROR.getMessage());
        }
        // DBに主旨があることをチェックする
        if (checkExist(topic, false) == false) {
            return new BaseRes(CommonMessage.TOPIC_NOT_FOUND.getCode(), CommonMessage.TOPIC_NOT_FOUND.getMessage());
        }
        // 編集時間を現時点にする
        topic.setUpdateDatetime(LocalDateTime.now());
        // 削除フラグを1にする
        topicDao.updateTopiceStatusByTopicId(topic);
        return new BaseRes(CommonMessage.REMOVE_SUCCESS.getCode(), CommonMessage.REMOVE_SUCCESS.getMessage());
    }



    /**
     * 入力チェック
     * 
     * @param topic    主旨モデル
     * @param isCreate 作成であるかの真理値
     * @return 結果の真理値
     */
    private boolean checkParam(Topic topic, boolean isCreate) {
        // 主旨IDと主旨名がヌル、空文字列、空白文字列ではないこと
        if (!StringUtils.hasText(topic.getTopicId()) || !StringUtils.hasText(topic.getTopicName())) {
            return false;
        }
        // 作成や編集によって作成者と編集者をチェック
        if (isCreate) {
            // 作成の場合は作成者がヌル、空文字列、空白文字列ではないこと
            if(!StringUtils.hasText(topic.getCreater())){
                return false;
            }
        } else {
            // 編集の場合は編集者がヌル、空文字列、空白文字列ではないこと
            if (!StringUtils.hasText(topic.getUpdater())) {
                return false;
            }
        }
        return true;
    }

    /**
     * 桁数チェック
     * 
     * @param topic    主旨モデル
     * @param isCreate 作成であるかの真理値
     * @return 結果の真理値
     */
    private boolean checkByte(Topic topic, boolean isCreate) {
        // 主旨ID、主旨名、主旨コメントの桁数をチェックする
        if (topic.getTopicId().length() > AppConstants.DataLength.TOPIC_ID.getLength()
         || topic.getTopicName().length() > AppConstants.DataLength.TOPIC_NAME.getLength()
         || topic.getComment().length() > AppConstants.DataLength.TOPIC_COMMENT.getLength()) {
            return false;
        }
        // 作成や編集によって作成者と編集者をチェック
        if (isCreate) {
            // 作成の場合は作成者の桁数をチェックする
            if(topic.getCreater().length() > AppConstants.DataLength.CREATER.getLength()){
                return false;
            }
        } else {
            // 編集の場合は編集者の桁数をチェックする
            if (topic.getUpdater().length() > AppConstants.DataLength.UPDATER.getLength()) {
                return false;
            }
        }
        return true;
    }

    /**
     * DBに請求書があるかチェック
     * 
     * @param topic     主旨モデル
     * @param isCreate  作成であるかの真理値
     * @return 結果の真理値
     */
    private boolean checkExist(Topic topic, boolean isCreate) {
        //主旨IDで主旨を取得する
        Topic selectedTopic = topicDao.selectTopicByTopicId(topic.getTopicId());
        // 作成かどうかによって違う確認を行う
        if (isCreate) {
            // 作成の場合は主旨名もチェックする：主旨名で主旨名を取得する
            String selectedTopicName = topicDao.selectTopicNameByTopicName(topic.getTopicName());
            // 新規の場合、selectedTopicとselectedTopicNameはヌルのはず
            if (selectedTopic != null || StringUtils.hasText(selectedTopicName)) {
                return false;
            }
        } else {
            // 編集と削除の場合、selectedTopicはヌルではないはず
            if (selectedTopic == null) {
                return false;
            }
        }
        return true;
    }
}
