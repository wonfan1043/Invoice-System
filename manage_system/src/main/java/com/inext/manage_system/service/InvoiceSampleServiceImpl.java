package com.inext.manage_system.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inext.manage_system.dao.InvoiceSampleDao;
import com.inext.manage_system.enums.CommonMessage;
import com.inext.manage_system.model.InvoiceSample;
import com.inext.manage_system.dto.BaseRes;
import com.inext.manage_system.dto.InvoiceSampleSearchRes;

import org.springframework.util.StringUtils;

@Service
public class InvoiceSampleServiceImpl implements InvoiceSampleService {

    @Autowired
    InvoiceSampleDao invoiceSampleDao;

    /**
     * 会社サンプルリスト取得
     * 
     * @return 会社サンプルリストと結果メッセージ
     */
    @Override
    public List<InvoiceSample> getSampleList(){
        // 会社サンプルリストを取得する
        return invoiceSampleDao.selectSampleList();
    }

    /**
     * 会社サンプル取得
     * 
     * @param corpName 会社名
     * @return 会社サンプルと結果メッセージ
     */
    @Override
    public InvoiceSampleSearchRes getSample(String corpName){
        // 入力チェック                                                                                                                                                                                
        if(!StringUtils.hasText(corpName)){
            return new InvoiceSampleSearchRes(null, CommonMessage.PARAM_ERROR.getCode(), CommonMessage.PARAM_ERROR.getMessage());
        }
        // サンプル取得
        InvoiceSample searchResult = invoiceSampleDao.selectInvoiceSampleByCorpName(corpName);
        return new InvoiceSampleSearchRes(searchResult, CommonMessage.SUCCESS.getCode(), CommonMessage.SUCCESS.getMessage());
    }

    /**
     * 会社サンプル作成/編集
     * 
     * @param sample 会社サンプルモデル
     * @return 結果メッセージオブジェクト
     */
    @Override
    public BaseRes createOrUpdateSample(InvoiceSample sample) {
        // 入力チェック
        if (checkParam(sample) == false) {
            return new BaseRes(CommonMessage.PARAM_ERROR.getCode(),CommonMessage.PARAM_ERROR.getMessage());
        }
        // 桁数チェック
        if(checkByte(sample) == false){
            return new BaseRes(CommonMessage.DATA_TOO_BIG.getCode(),CommonMessage.DATA_TOO_BIG.getMessage());
        }
        // DBにサンプルがあるかどうかによって作成/編集する
        // ない場合は作成時間を現時点にしてサンプルを作成する
        if(checkExist(sample) == false){
            sample.setCreateDatetime(LocalDateTime.now());
            invoiceSampleDao.insertInvoiceSample(sample);
            return new BaseRes(CommonMessage.CREATE_SUCCESS.getCode(),CommonMessage.CREATE_SUCCESS.getMessage());
        } else {
            // ある場合は編集時間を現時点にしてサンプルを編集する
            sample.setUpdateDatetime(LocalDateTime.now());
            invoiceSampleDao.updateInvoiceSampleByCorpName(sample);
            return new BaseRes(CommonMessage.UPDATE_SUCCESS.getCode(), CommonMessage.UPDATE_SUCCESS.getMessage());
        }
    }

    /**
     * 入力チェック
     * 
     * @param sample 会社サンプルモデル
     * @return 結果の真理値
     */
    private boolean checkParam(InvoiceSample sample) {
        // 会社名、受取人、郵便番号、都道府県、市区町村、番地/住所、主旨はヌル、空文字列、空白文字列ではないこと
        // 銀行IDと税率はヌルではないこと
        // 作成者と編集者は、少なくとも1つはヌル、空文字列、空白文字列ではないこと
        if (!StringUtils.hasText(sample.getCorpName())
         || !StringUtils.hasText(sample.getReceiver())
         || !StringUtils.hasText(sample.getPostcode())
         || !StringUtils.hasText(sample.getCounty())
         || !StringUtils.hasText(sample.getTown())
         || !StringUtils.hasText(sample.getAddress())
         || !StringUtils.hasText(sample.getTopicName())
         || sample.getBankId() == null
         || sample.getTax() == null
         || (!StringUtils.hasText(sample.getCreator()) && !StringUtils.hasText(sample.getUpdater()))) {
            return false;
        }
        return true;
    }

    /**
     * 桁数チェック
     * 
     * @param sample 会社サンプルモデル
     * @return 結果の真理値
     */
    private boolean checkByte(InvoiceSample sample) {
        // 10バイト以内：郵便番号
        // 20バイト以内：会社名、受取人、都道府県、市区町村、作成者と編集者
        // 45バイト以内：番地/住所、ビル名/部屋番号など、主旨
        if (sample.getCorpName().length() > 20
         || sample.getReceiver().length() > 20
         || sample.getPostcode().length() > 10
         || sample.getCounty().length() > 20
         || sample.getTown().length() > 20
         || sample.getAddress().length() > 45
         || sample.getBuilding().length() > 45
         || sample.getTopicName().length() > 45
         || (sample.getCreator().length() > 20 || sample.getUpdater().length() > 20)) {
            return false;
        }
        return true;
    }

    /**
     * DBにサンプルがあるかチェック
     * 
     * @param sample 会社サンプルモデル
     * @return 結果の真理値
     */
    private boolean checkExist(InvoiceSample sample) {
        // 会社名でサンプルを取得する
        InvoiceSample selectedSample = invoiceSampleDao.selectInvoiceSampleByCorpName(sample.getCorpName());
        // 出力したサンプルがヌルの場合はfalseを返す
        if(selectedSample == null){
            return false;
        }
        return true;
    }
}
