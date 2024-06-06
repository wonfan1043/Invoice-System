package com.inext.manage_system.controller.manager;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.inext.manage_system.dto.BaseRes;
import com.inext.manage_system.dto.SearchTopicReq;
import com.inext.manage_system.dto.SearchTopicRes;
import com.inext.manage_system.dto.TopicContentRes;
import com.inext.manage_system.enums.AppConstants;
import com.inext.manage_system.model.Topic;
import com.inext.manage_system.service.TopicService;

import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TopicServiceController {

    @Autowired
    private TopicService topicService;

    /**
     * 主旨リストページ
     * 
     * @param model モデルオブジェクト
     * @return テンプレートのパス
     */
    @GetMapping("/manager/invoice/topic_list")
    public String topicListPageController(Model model){
        // 検索条件：年を「当年」に設定する
        int year = LocalDate.now().getYear();
        // 検索条件：月を「当月」に設定する
        int month = LocalDate.now().getMonthValue();
        // 検索条件：キーワードを「ヌル」に設定する
        String keyword = null;
        // searchTopicReqを宣言する
        SearchTopicReq searchTopicReq = new SearchTopicReq(AppConstants.DEFAULT_PAGE_NUM, year, month, keyword);

        // // 主旨リストを取得
        SearchTopicRes searchTopicRes = topicService.searchTopic(searchTopicReq);

        // 主旨リストを追加する
        model.addAttribute("topicList", searchTopicRes.getTopicList());

        return "/manager/Topic";
    }
    
    /**
     * 主旨を検索する
     * 
     * @param model モデルオブジェクト
     * @return テンプレートのパス
     */
    @PostMapping("/manager/invoice/search_topic")
    public String searchTopicController(@RequestBody SearchTopicReq searchTopicReq, Model model){

        // 主旨リストを取得する
        SearchTopicRes searchTopicRes = topicService.searchTopic(searchTopicReq);

        // 主旨リストを追加する
        model.addAttribute("topicList", searchTopicRes.getTopicList());

        return "/manager/Topic::list";
    }

    /**
     * 主旨削除
     * 
     * @param topic 削除する主旨
     * @return 結果メッセージオブジェクト
     */
    @PostMapping("/manager/invoice/delete_topic")
    @ResponseBody
    public BaseRes deleteTopicController(@RequestBody Topic topic){
        // 主旨を削除して、結果を取得する
        return topicService.deleteTopic(topic);
    }

    /**
     * 主旨作成ページ
     * 
     * @return テンプレートのパス
     */
    @GetMapping("/manager/invoice/create_topic")
    public String createTopicPageController(){
        return "/manager/TopicCreate";

    }
    
    /**
     * 主旨作成
     * 
     * @param topic 作成する主旨
     * @return 結果メッセージオブジェクト
     */
    @PostMapping("/manager/invoice/save_topic")   
    @ResponseBody
    public BaseRes createTopicController(@RequestBody Topic topic){
        // 主旨を作成して結果を取得する
        return topicService.createTopic(topic);
    }
    
    /**
     * 主旨編集ページ
     * 
     * @param topicId 主旨ID
     * @param model モデルオブジェクト
     * @return  テンプレートのパス
     */
    @GetMapping("/manager/invoice/edit_topic")
    public String editTopicPageController(@RequestParam(required = true) String topicId, Model model){
        // 主旨詳細を取得する
        TopicContentRes topicContentRes = topicService.checkTopicContent(topicId);

        // 主旨詳細を追加する
        model.addAttribute("topicData", topicContentRes.getTopic());

        return "/manager/TopicEdit";
    }
    
    /**
     * 主旨を編集する
     * 
     * @param topic 編集する主旨
     * @return 結果メッセージオブジェクト
     */
    @PostMapping("/manager/invoice/update_topic")
    @ResponseBody
    public BaseRes updateTopicController(@RequestBody Topic topic){
        // 主旨を編集して結果を取得する
        return topicService.updateTopic(topic);
    }

}
