package com.inext.manage_system.controller.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.inext.manage_system.dto.BaseRes;
import com.inext.manage_system.dto.InvoiceSampleSearchRes;
import com.inext.manage_system.model.InvoiceSample;
import com.inext.manage_system.service.InvoiceSampleService;

@Controller
public class InvoiceSampleServiceController {

    @Autowired
    private InvoiceSampleService invoiceSampleService;

    /**
     * 請求書サンプルを作成/編集する
     * 
     * @param sample 請求書サンプルモデル
     * @return 結果メッセージオブジェクト
     */
    @PostMapping("/manager/invoice/save_sample")
    @ResponseBody
    public BaseRes saveInvoiceSample(@RequestBody InvoiceSample sample){
        // 請求書サンプルを作成/編集して、結果を取得する
        return invoiceSampleService.createOrUpdateSample(sample);
    }

    /**
     * 請求書サンプルを取得する
     * 
     * @param corpName 会社名
     * @return 請求書サンプルモデルと結果メッセージ
     */
    @GetMapping("/manager/invoice/get_sample")
    @ResponseBody
    public InvoiceSampleSearchRes getInvoiceSample(@RequestParam(required = true) String corpName){
        // 請求書サンプルを取得して返す
        return invoiceSampleService.getSample(corpName);
    }

}