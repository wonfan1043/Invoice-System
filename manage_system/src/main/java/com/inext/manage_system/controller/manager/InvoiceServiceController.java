package com.inext.manage_system.controller.manager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.inext.manage_system.dao.BankDao;
import com.inext.manage_system.dao.CompanyDao;
import com.inext.manage_system.dao.ProductDao;
import com.inext.manage_system.dto.BaseRes;
import com.inext.manage_system.dto.CreateOrUpdateInvoiceReq;
import com.inext.manage_system.dto.InvoiceContentRes;
import com.inext.manage_system.dto.SearchInvoiceReq;
import com.inext.manage_system.dto.SearchInvoiceRes;
import com.inext.manage_system.enums.AppConstants;
import com.inext.manage_system.model.Bank;
import com.inext.manage_system.model.Company;
import com.inext.manage_system.model.Invoice;
import com.inext.manage_system.model.InvoiceSample;
import com.inext.manage_system.model.Product;
import com.inext.manage_system.model.Topic;
import com.inext.manage_system.service.InvoiceSampleService;
import com.inext.manage_system.service.InvoiceService;
import com.inext.manage_system.service.TopicService;

@Controller
public class InvoiceServiceController {

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private InvoiceSampleService invoiceSampleService;
    
    @Autowired
    private TopicService topicService;

    @Autowired
    private CompanyDao companyDao;

    @Autowired
    private BankDao bankDao;

    @Autowired
    private ProductDao productDao;

    /**
     * 請求書リストページ
     * 
     * @param model モデルオブジェクト
     * @return テンプレートのパス
     */
    @GetMapping("/manager/invoice/invoice_list")
    public String invoiceList(Model model){
        // 協力会社リストを取得する
        List<Company> companyList = companyDao.selectCorpList();

        // 検索条件：日付タイプを「作成日付」に設定する
        int dateType = AppConstants.DateType.CREATE_DATE.getCode();
        // 検索条件：年を「当年」に設定する
        int year = LocalDate.now().getYear();
        // 検索条件：月を「当月」に設定する
        int month = LocalDate.now().getMonthValue();
        // 検索条件：協力会社を「ヌル」に設定する
        String corpName = null;

        // ページ番号
        int pageNum = 1;
        // ページ数
        int pageSize = 7;

        // 請求書リストを取得する
        SearchInvoiceReq req = new SearchInvoiceReq(pageNum, dateType, year, month, corpName);
        PageHelper.startPage(pageNum, pageSize);
        SearchInvoiceRes searchResult = invoiceService.searchInvoice(req);
        PageInfo<Invoice> invoiceList = new PageInfo<>(searchResult.getInvoiceList());

        // 協力会社リストを追加する
        model.addAttribute("companyList", companyList);

        // 請求書リストを追加する
        model.addAttribute("invoiceList", invoiceList);

        return "/manager/InvoiceList";
    }

    /**
     * 請求書を検索する
     * 
     * @param req   請求書検索オブジェクト
     * @param model モデルオブジェクト
     * @return テンプレートのパス
     */
    @PostMapping("/manager/invoice/search_invoice")
    public String searchInvoice(@RequestBody SearchInvoiceReq req, Model model){
        // ページ番号
        int defaultPageNumber = 1;
        // 実際のページ番号
        Integer finalPageNumber = req.getPageNum() != null ? req.getPageNum() : defaultPageNumber;
        // ページ数
        int pageSize = 7;

        // 請求書リストを取得
        PageHelper.startPage(finalPageNumber, pageSize);
        SearchInvoiceRes searchResult = invoiceService.searchInvoice(req);

        // 主旨リストがnullでないことを確認
        List<Invoice> invoiceList = searchResult.getCode() == 1012 ? searchResult.getInvoiceList() : new ArrayList<>();

        // 主旨リストを追加する
        model.addAttribute("invoiceList", new PageInfo<>(invoiceList));

        return "/manager/InvoiceList::list";
    }

    /**
     * 請求書を削除する
     * 
     * @param invoice 削除する請求書
     * @return 結果メッセージオブジェクト
     */
    @PostMapping("/manager/invoice/delete_invoice")
    @ResponseBody
    public BaseRes deleteInvoice(@RequestBody Invoice invoice){
        // 請求書サンプルを削除して、結果を取得する
        return invoiceService.deleteInvoice(invoice);
    }

    /**
     * 請求書明細プレビューページ
     * 
     * @param invoiceNo 請求書番号
     * @param model     モデルオブジェクト
     * @return テンプレートのパス
     */
    @GetMapping("/manager/invoice/invoice_view")
    public String checkInvoice(@RequestParam(required = true) String invoiceNo, Model model){
        // 請求書詳細を取得する
        InvoiceContentRes invoiceContent = invoiceService.checkInvoiceContent(invoiceNo);

        // 請求書内容を追加する
        model.addAttribute("invoice", invoiceContent.getInvoice());

        // 口座情報を追加する
        model.addAttribute("bank", invoiceContent.getBank());

        // 請求内容を追加する
        model.addAttribute("product", invoiceContent.getProduct());

        return "/manager/InvoiceView";
    }

    /**
     * 新規作成ページ
     * 
     * @param model モデルオブジェクト
     * @return テンプレートのパス
     */
    @GetMapping("/manager/invoice/create_invoice")
    public String createInvoice(Model model){
        // サンプルリストを取得する
        List<InvoiceSample> sampleList = invoiceSampleService.getSampleList();

        // 採番を取得する
        int newNum = invoiceService.getNewNumber();

        // 消費税リストを取得する
        List<Float> taxList = invoiceService.getTaxRateList();

        // 協力会社リストを取得する
        List<Company> companyList = companyDao.selectCorpList();

        // 主旨リストを取得する
        List<Topic> topicList = topicService.getTopicList();

        // 銀行リストを取得する
        List<Bank> bankList = bankDao.selectBankList();

        // 商品リストを取得する
        List<Product> productList = productDao.selectProduct();

        // サンプルリストを追加する
        model.addAttribute("sampleList", sampleList);

        // 採番を追加する
        model.addAttribute("newNum", newNum);

        // 消費税リストを追加する
        model.addAttribute("taxList", taxList);

        // 協力会社リストを追加する
        model.addAttribute("companyList", companyList);

        // 主旨リストを追加する
        model.addAttribute("topicList", topicList);

        // 口座リストを追加する
        model.addAttribute("bankList", bankList);

        // 商品リストを追加する
        model.addAttribute("productList", productList);

        return "/manager/InvoiceCreate";
    }

    /**
     * 口座詳細を取得する
     * 
     * @param bankId 銀行ID
     * @return 銀行モデル
     */
    @GetMapping("/manager/invoice/get_bank_info")
    @ResponseBody
    public Bank getBankInfo(@RequestParam(required = true) int bankId){
        // 口座詳細を取得して返す
        return bankDao.selectBankAccountByBankId(bankId);
    }

    /**
     * 請求書を作成する
     * 
     * @param req 作成する請求書
     * @return 結果メッセージオブジェクト
     */
    @PostMapping("/manager/invoice/save_invoice")
    @ResponseBody
    public BaseRes saveInvoice(@RequestBody CreateOrUpdateInvoiceReq req){
        // 請求書を作成して結果を取得する
        return invoiceService.createInvoice(req);
    }

    /**
     * 請求書編集ページ
     * 
     * @param model モデルオブジェクト
     * @return テンプレートのパス
     */
    @GetMapping("/manager/invoice/edit_invoice")
    public String invoiceEdit(Model model){
        // サンプルリストを取得する
        List<InvoiceSample> sampleList = invoiceSampleService.getSampleList();

        // 消費税リストを取得する
        List<Float> taxList = invoiceService.getTaxRateList();

        // 協力会社リストを取得する
        List<Company> companyList = companyDao.selectCorpList();

        // 主旨リストを取得する
        List<Topic> topicList = topicService.getTopicList();

        // 銀行リストを取得する
        List<Bank> bankList = bankDao.selectBankList();

        // 商品リストを取得する
        List<Product> productList = productDao.selectProduct();

        // サンプルリストを追加する
        model.addAttribute("sampleList", sampleList);

        // 消費税リストを追加する
        model.addAttribute("taxList", taxList);

        // 協力会社リストを追加する
        model.addAttribute("companyList", companyList);

        // 主旨リストを追加する
        model.addAttribute("topicList", topicList);

        // 銀行リストを追加する
        model.addAttribute("bankList", bankList);

        // 商品リストを追加する
        model.addAttribute("productList", productList);

        return "/manager/InvoiceEdit";
    }

    /**
     * 請求書を編集する
     * 
     * @param req 編集する請求書
     * @return 結果メッセージオブジェクト
     */
    @PostMapping("/manager/invoice/update_invoice")
    @ResponseBody
    public BaseRes updateInvoice(@RequestBody CreateOrUpdateInvoiceReq req){
        // 請求書を編集して結果を取得する
        return invoiceService.updateInvoice(req);
    }

}
