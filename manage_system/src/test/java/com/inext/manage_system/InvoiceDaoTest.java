// package com.inext.manage_system;

// import java.time.LocalDate;
// import java.util.List;

// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;

// import com.inext.manage_system.dao.InvoiceDao;
// import com.inext.manage_system.dto.SearchInvoiceReq;
// import com.inext.manage_system.dto.SearchInvoiceRes;
// import com.inext.manage_system.enums.DateType;
// import com.inext.manage_system.model.Invoice;
// import com.inext.manage_system.service.InvoiceService;

// @SpringBootTest
// public class InvoiceDaoTest {

//     @Autowired
//     private InvoiceDao invoiceDao;
    
//     @Autowired
//     private InvoiceService invoiceService;

//     @Test
//     public void searchInvoiceTest1(){
//         // 検索条件：日付タイプ
//         int dateType = DateType.CREATE_DATE.getCode();
//         // 検索条件：年
//         int year = 2024;
//         // int year = LocalDate.now().getYear();
//         // 検索条件：月
//         int month = 4;
//         // int month = LocalDate.now().getMonthValue();
//         // 検索条件：会社ID
//         int corpId = 1;

//         System.out.println(dateType);
//         System.out.println(year);
//         System.out.println(month);
//         System.out.println(corpId);

//         // 時間帯チェック
//         // (1)入力された年と月、及び1日でtimeRangeを宣言します
//         LocalDate timeRange = LocalDate.of(year, month, 1);
//         System.out.println(timeRange);
//         // (2)timeRangeが現時点より遅い場合はエラーメッセージを返します
//         if(timeRange.isAfter(LocalDate.now())){
//             System.out.println("CommonMessage.INVALID_TIME_RANGE");
//         }
//         // InvoiceDaoの「請求書取得 by dateType, corpId」メソッドで請求書を取得します
//         SearchInvoiceReq req = new SearchInvoiceReq(dateType, year, month, corpId);
//         System.out.println(req);
//         List<Invoice> invoiceList = invoiceDao.selectInvoiceByDateTypeCorpId(req);
//         if (invoiceList.isEmpty()) {
//             // invoiceListがヌルの場合はエラーメッセージを追加します
//             System.out.println("CommonMessage.INVOICE_NOT_FOUND");
//         } else {
//             // invoiceListがヌルではない場合は、取得したリストと成功メッセージを追加します
//             System.out.println("CommonMessage.SUCCESS");
//         }
//     }

//     @Test
//     public void searchInvoiceTest2(){
//         // 検索条件：日付タイプ
//         int dateType = DateType.CREATE_DATE.getCode();
//         // 検索条件：年
//         int year = 2024;
//         // 検索条件：月
//         int month = 4;
//         // 検索条件：会社ID
//         int corpId = 1;

//         // SearchInvoiceReq req = new SearchInvoiceReq(dateType, year, month, corpId);
//         // SearchInvoiceRes searchResult = invoiceService.searchInvoice(req);
//         // System.out.println(searchResult);
//     }

// }
