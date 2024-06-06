package com.inext.manage_system.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inext.manage_system.dao.BankDao;
import com.inext.manage_system.dao.ChargeContentDao;
import com.inext.manage_system.dao.InvoiceDao;
import com.inext.manage_system.dao.InvoiceNoDao;
import com.inext.manage_system.enums.AppConstants;
import com.inext.manage_system.enums.CommonMessage;
import com.inext.manage_system.model.Bank;
import com.inext.manage_system.model.ChargeContent;
import com.inext.manage_system.model.Invoice;
import com.inext.manage_system.dto.BaseRes;
import com.inext.manage_system.dto.CreateOrUpdateInvoiceReq;
import com.inext.manage_system.dto.InvoiceContentRes;
import com.inext.manage_system.dto.SearchInvoiceReq;
import com.inext.manage_system.dto.SearchInvoiceRes;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Transactional
@Service
public class InvoiceServiceImpl implements InvoiceService {

    @Autowired
    InvoiceDao invoiceDao;

    @Autowired
    InvoiceNoDao invoiceNoDao;

    @Autowired
    BankDao bankDao;

    @Autowired
    ChargeContentDao chargeContentDao;

    /**
     * 採番取得
     * 
     * @return 新番号
     */
    @Override
    public int getNewNumber(){
        // 最後の番号を取得する
        int lastNum = invoiceNoDao.selectInvoiceNoByOrder();
        // 新番号を作成する
        int newNum = lastNum + 1;
        return newNum;
    }

    /**
     * 消費税リスト取得
     * 
     * @return 消費税リスト
     */
    @Override
    public List<Float> getTaxRateList(){
        return invoiceDao.selectTaxRateDistinct();
    }


    /**
     * 新規作成
     * 
     * @param req 作成する請求書
     * @return 結果メッセージオブジェクト
     */
    @Override
    public BaseRes createInvoice(CreateOrUpdateInvoiceReq req) {
        // 入力チェック
        if (checkParam(req, true) == false) {
            return new BaseRes(CommonMessage.PARAM_ERROR.getCode(), CommonMessage.PARAM_ERROR.getMessage());
        }
        // 桁数チェック
        if (checkByte(req, true) == false) {
            return new BaseRes(CommonMessage.DATA_TOO_BIG.getCode(), CommonMessage.DATA_TOO_BIG.getMessage());
        }
        // 請求書番号フォーマットチェック
        String invoiceNoPattern = "^[A-Z]{1}\\d{8}$";
        if (!req.getInvoice().getInvoiceNo().matches(invoiceNoPattern)){
            return new BaseRes(CommonMessage.INVALID_INVOICE_NO.getCode(), CommonMessage.INVALID_INVOICE_NO.getMessage());
        }
        // DBに重複の請求書がないことをチェックする
        if (checkExist(req.getInvoice().getInvoiceNo(), true) == false){
            return new BaseRes(CommonMessage.INVOICE_ALREADY_EXISTS.getCode(), CommonMessage.INVOICE_ALREADY_EXISTS.getMessage());
        }
        // 請求書を追加する
        // (1)請求書番号のタイプコードを取得して新番号を追加
        String[] invoiceNoArr = req.getInvoice().getInvoiceNo().split("");
        invoiceNoDao.insertInvoiceNo(invoiceNoArr[0]);
        // (2)作成時間を現時点にして請求書を追加する
        req.getInvoice().setCreateDatetime(LocalDateTime.now());
        invoiceDao.insertInvoice(req.getInvoice());
        // (3)請求内容を追加する
        chargeContentDao.insertChargeContent(req.getProducts());
        return new BaseRes(CommonMessage.CREATE_SUCCESS.getCode(), CommonMessage.CREATE_SUCCESS.getMessage());
    }

    /**
     * 請求書編集
     * 
     * @param req 編集する請求書
     * @return 結果メッセージオブジェクト
     */
    @Override
    public BaseRes updateInvoice(CreateOrUpdateInvoiceReq req) {
        // 入力チェック
        if (checkParam(req, false) == false) {
            return new BaseRes(CommonMessage.PARAM_ERROR.getCode(), CommonMessage.PARAM_ERROR.getMessage());
        }
        // 桁数チェック
        if (checkByte(req, false) == false) {
            return new BaseRes(CommonMessage.DATA_TOO_BIG.getCode(), CommonMessage.DATA_TOO_BIG.getMessage());
        }
        // 請求書番号フォーマットチェック
        String invoiceNoPattern = "^[A-Z]{1}\\d{8}$";
        if (!req.getInvoice().getInvoiceNo().matches(invoiceNoPattern)){
            return new BaseRes(CommonMessage.INVALID_INVOICE_NO.getCode(), CommonMessage.INVALID_INVOICE_NO.getMessage());
        }
        // DBに請求書があることをチェックする
        if (checkExist(req.getInvoice().getInvoiceNo(), false) == false){
            return new BaseRes(CommonMessage.INVOICE_NOT_FOUND.getCode(), CommonMessage.INVOICE_NOT_FOUND.getMessage());
        }
        // 請求書を編集する
        // (1)編集時間を現時点にして請求書を編集する
        req.getInvoice().setUpdateDatetime(LocalDateTime.now()); //
        invoiceDao.updateInvoice(req.getInvoice());
        // (2)DBに請求内容があれば削除
        int chargeContentNum = chargeContentDao.countChargeContentNumByInvoiceNo(req.getInvoice().getInvoiceNo());
        if(chargeContentNum > 0){
            chargeContentDao.deleteChargeContentByInvoiceNo(req.getInvoice().getInvoiceNo());
        }
        // (3)請求内容を保存
        chargeContentDao.insertChargeContent(req.getProducts());
        return new BaseRes(CommonMessage.UPDATE_SUCCESS.getCode(), CommonMessage.UPDATE_SUCCESS.getMessage());
    }

    /**
     * 請求書検索
     * 
     * @param req 検索条件オブジェクト
     * @return 請求書リストと結果メッセージ
     */
    @Override
    public SearchInvoiceRes searchInvoice(SearchInvoiceReq req) {
        // 入力チェック：日付タイプが請求日や作成日であること
        if (req.getDateType() != AppConstants.DateType.CHARGE_DATE.getCode() && req.getDateType() != AppConstants.DateType.CREATE_DATE.getCode()){
            return new SearchInvoiceRes(null, CommonMessage.PARAM_ERROR.getCode(), CommonMessage.PARAM_ERROR.getMessage());
        }
        // 検索条件：年と月のチェック
        // (1)現時点の年と月を宣言する
        int currentYearAndMonth = (LocalDate.now().getYear() * 100) + LocalDate.now().getMonthValue();
        // (2)検索条件の年と月を宣言する
        int searchYearAndMonth = (req.getYear() * 100) + req.getMonth();
        // (3)検索条件の年と月は現時点より遅くないことをチェックする
        if (currentYearAndMonth < searchYearAndMonth){
            return new SearchInvoiceRes(null, CommonMessage.INVALID_TIME_RANGE.getCode(), CommonMessage.INVALID_TIME_RANGE.getMessage());
        }
        // 請求書を取得する
        List<Invoice> invoiceList = invoiceDao.selectInvoiceByDateTypeCorpName(req);
        // invoiceListがヌルの場合は請求書見つからなかったメッセージを返す
        if (invoiceList.isEmpty()) {
            return new SearchInvoiceRes(invoiceList, CommonMessage.INVOICE_NOT_FOUND.getCode(), CommonMessage.INVOICE_NOT_FOUND.getMessage());
        }
        return new SearchInvoiceRes(invoiceList, CommonMessage.SUCCESS.getCode(), CommonMessage.SUCCESS.getMessage());
    }

    /**
     * 請求書詳細確認
     * 
     * @param invoiceNo 請求書番号
     * @return 請求書詳細、口座情報、請求内容と結果メッセージ
     */
    @Override
    public InvoiceContentRes checkInvoiceContent(String invoiceNo) {
        // 入力チェック：請求書番号がヌル、空文字列、空白文字列ではないこと
        if (!StringUtils.hasText(invoiceNo)) {
            return new InvoiceContentRes(null, null, null, CommonMessage.PARAM_ERROR);
        }
        // 請求書番号フォーマットチェック
        String pattern = "^[A-Z]{1}\\d{8}$";
        if (!invoiceNo.matches(pattern)) {
            return new InvoiceContentRes(null, null, null, CommonMessage.INVALID_INVOICE_NO);
        }
        // 請求書詳細を取得する
        Invoice selectedInvoice = invoiceDao.selectInvoiceByInvoiceNo(invoiceNo);
        // 請求書見つからない場合はメッセージを返す
        if (selectedInvoice == null) {
            return new InvoiceContentRes(null, null, null, CommonMessage.INVOICE_NOT_FOUND);
        }
        // 請求書見つかった場合は
        // (1)取得したbank_idで口座情報を取得する
        Bank selectedBank = bankDao.selectBankAccountByBankId(selectedInvoice.getBankId());
        // (2)invoiceNoで請求内容を取得する
        List<ChargeContent> selectedProducts = chargeContentDao.selectAllChargeContentByInvoiceNo(invoiceNo);
        return new InvoiceContentRes(selectedInvoice, selectedBank, selectedProducts, CommonMessage.SUCCESS);
    }

    /**
     * 請求書削除
     * 
     * @param invoice 請求書モデル
     * @return 結果メッセージオブジェクト
     */
    @Override
    public BaseRes deleteInvoice(Invoice invoice) {
        // 入力チェック：請求書番号と編集者がヌル、空文字列、空白文字列ではないこと
        if (!StringUtils.hasText(invoice.getInvoiceNo()) || !StringUtils.hasText(invoice.getUpdater())) {
            return new BaseRes(CommonMessage.PARAM_ERROR.getCode(), CommonMessage.PARAM_ERROR.getMessage());
        }
        // 請求書番号フォーマットチェック
        String pattern = "^[A-Z]{1}\\d{8}$";
        if (!invoice.getInvoiceNo().matches(pattern)) {
            return new BaseRes(CommonMessage.INVALID_INVOICE_NO.getCode(), CommonMessage.INVALID_INVOICE_NO.getMessage());
        }
        // DBに請求書があることをチェックする
        if (checkExist(invoice.getInvoiceNo(), false) == false) {
            return new BaseRes(CommonMessage.INVOICE_NOT_FOUND.getCode(), CommonMessage.INVOICE_NOT_FOUND.getMessage());
        }
        // 編集時間を現時点にして、削除フラグを1に編集する
        invoice.setUpdateDatetime(LocalDateTime.now());
        invoiceDao.updateInvoiceStatus(invoice);
        // DBに請求内容があるかチェックする
        int chargeContentNum = chargeContentDao.countChargeContentNumByInvoiceNo(invoice.getInvoiceNo());
        // 請求内容がある場合は削除フラグを1に編集する
        if (chargeContentNum > 0) {
            // ChargeContentモデルを宣言して、請求書番号、編集者と編集時間を設定する
            ChargeContent contentToDelete = new ChargeContent();
            contentToDelete.setInvoiceNo(invoice.getInvoiceNo());
            contentToDelete.setUpdater(invoice.getUpdater());
            contentToDelete.setUpdateDatetime(LocalDateTime.now());
            // 削除フラグを1に編集する
            chargeContentDao.updateChargeContentStatusByInvoiceNo(contentToDelete);
        }
        return new BaseRes(CommonMessage.REMOVE_SUCCESS.getCode(), CommonMessage.REMOVE_SUCCESS.getMessage());
    }



    /**
     * 入力チェック
     * 
     * @param req      作成/編集する請求書
     * @param isCreate 作成であるかの真理値
     * @return 結果の真理値
     */
    private boolean checkParam(CreateOrUpdateInvoiceReq req, boolean isCreate) {
        // 請求書番号、会社名、受取人、郵便番号、都道府県、市区町村、番地/住所、主旨はヌル、空文字列、空白文字列ではないこと
        // 銀行ID、税率、 請求内容はヌルではないこと
        if (!StringUtils.hasText(req.getInvoice().getInvoiceNo())
         || !StringUtils.hasText(req.getInvoice().getCorpName())
         || !StringUtils.hasText(req.getInvoice().getReceiver())
         || !StringUtils.hasText(req.getInvoice().getPostcode())
         || !StringUtils.hasText(req.getInvoice().getCounty())
         || !StringUtils.hasText(req.getInvoice().getTown())
         || !StringUtils.hasText(req.getInvoice().getAddress())
         || !StringUtils.hasText(req.getInvoice().getTopicName())
         || req.getInvoice().getBankId() == null
         || req.getInvoice().getTax() == null
         || CollectionUtils.isEmpty(req.getProducts())) {
            return false;
        }
        // 作成や編集によって作成者と編集者をチェックする
        if (isCreate) {
            // 作成の場合は作成者がヌル、空文字列、空白文字列ではないこと
            if(!StringUtils.hasText(req.getInvoice().getCreator())){
                return false;
            }
        } else {
            // 編集の場合は編集者がヌル、空文字列、空白文字列ではないこと
            if (!StringUtils.hasText(req.getInvoice().getUpdater())) {
                return false;
            }
        }
        return true;
    }

    /**
     * 桁数チェック
     * 
     * @param req      作成/編集する請求書
     * @param isCreate 作成であるかの真理値
     * @return 結果の真理値
     */
    private boolean checkByte(CreateOrUpdateInvoiceReq req, boolean isCreate) {
        // 9バイト：請求書番号
        // 10バイト以内：郵便番号
        // 20バイト以内：会社名、受取人、都道府県、市区町村
        // 45バイト以内：番地/住所、ビル名/部屋番号など、主旨
        // 50バイト以内：メモ
        if (req.getInvoice().getInvoiceNo().length() != 9
         || req.getInvoice().getCorpName().length() > 20
         || req.getInvoice().getPostcode().length() > 10
         || req.getInvoice().getReceiver().length() > 20
         || req.getInvoice().getCounty().length() > 20 
         || req.getInvoice().getTown().length() > 20
         || req.getInvoice().getAddress().length() > 45 
         || req.getInvoice().getBuilding().length() > 45
         || req.getInvoice().getTopicName().length() > 20 
         || req.getInvoice().getMemo().length() > 50) {
            return false;
        }
        // 作成や編集によって作成者と編集者をチェックする
        if (isCreate) {
            // 作成の場合は作成者の桁数が20バイト以内をチェックする
            if(req.getInvoice().getCreator().length() > 20){
                return false;
            }
        } else {
            // 編集の場合は編集者の桁数が20バイト以内をチェックする
            if (req.getInvoice().getUpdater().length() > 20) {
                return false;
            }
        }
        return true;
    }

    /**
     * DBに請求書があるかチェック
     * 
     * @param invoiceNo 請求書番号
     * @param isCreate  作成であるかの真理値
     * @return 結果の真理値
     */
    private boolean checkExist(String invoiceNo, boolean isCreate) {
        // 入力された請求書番号でデータを取得する
        String selectedInvoiceNo = invoiceDao.selectInvoiceNoByInvoiceNo(invoiceNo);
        // 作成であるかによってチェックする
        if (isCreate) {
            // 新規の場合、ヌルのはず
            if (StringUtils.hasText(selectedInvoiceNo)) {
                return false;
            }
        } else {
            // 編集と削除の場合、ヌルではないはず
            if (!StringUtils.hasText(selectedInvoiceNo)) {
                return false;
            }
        }
        return true;
    }

}
