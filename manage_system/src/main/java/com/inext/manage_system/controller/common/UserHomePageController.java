// package com.inext.manage_system.controller.common;

// import javax.mail.MessagingException;

// import org.springframework.lang.Nullable;
// import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.servlet.mvc.support.RedirectAttributes;

// import com.github.pagehelper.PageInfo;
// import com.inext.manage_system.common.SessionUtils;
// import com.inext.manage_system.dto.AccountDto;
// import com.inext.manage_system.enums.CommonMessage;
// import com.inext.manage_system.enums.MailSentMode;
// import com.inext.manage_system.enums.Status;
// import com.inext.manage_system.model.news.News;
// import com.inext.manage_system.service.account.AccountService;
// import com.inext.manage_system.service.news.NewsService;

// import jakarta.annotation.Resource;
// import jakarta.servlet.http.HttpServletRequest;

// /**
//  * 作成者:INEXT_奥田 日付 2023/09/01
//  */
// @Controller
// public class UserHomePageController {

//     @Resource
//     private NewsService newsService;
//     @Resource
//     private AccountService accountService;
//     @Resource
//     private SessionUtils sessionUtils;

//     /**
//      * ホームページを表示する。 作成者:INEXT_奥田
//      *
//      * @param pageNum  ページ番号
//      * @param pageSize ページサイズ
//      * @param model    モデルオブジェクト
//      * @return ホームページのビュー名
//      */
//     @GetMapping("/home")
//     public String homePage(@RequestParam(required = false) Integer pageNum,
//             @RequestParam(required = false) Integer pageSize, Model model) {

//         // ページ番号
//         Integer defaultPageNumber = 1;

//         // ページサイズ
//         Integer defaultPageSize = 10;

//         // 実際のページ番号
//         Integer allPageNumber = pageNum != null ? pageNum : defaultPageNumber;

//         // 実際のページサイズ
//         Integer allPageSize = pageSize != null ? pageSize : defaultPageSize;

//         // トップニュースのページ情報を取得
//         PageInfo<News> topicNewsPage = newsService.getNewsList(allPageNumber, allPageSize,
//                 Status.TOPIC_NEWS.getStatusCode());

//         // トップニュースのページ情報を追加
//         model.addAttribute("topicNewsPageInfo", topicNewsPage);

//         return "/common/UserHomePage";

//     }

//     /**
//      * ユーザーログインを行います。 作成者:INEXT_奥田
//      *
//      * @param accountDto         アカウントDTO
//      * @param request            HTTPリクエストオブジェクト
//      * @param redirectAttributes リダイレクト先に渡す属性情報を保持するオブジェクト
//      * @return リダイレクト先のURL
//      */
//     @PostMapping("/user/login")
//     public String userLogin(AccountDto accountDto, HttpServletRequest request, RedirectAttributes redirectAttributes) {
//         String userRole = Status.ACCOUNT_ROLE_USER.getStatusName();

//         // ユーザー情報を確認する
//         AccountDto loginResult = accountService.checkLoginUserIsExist(accountDto, userRole);

//         if (loginResult == null) {

//             // ログイン失敗した場合、メッセージを返す
//             redirectAttributes.addFlashAttribute("msg", CommonMessage.LOGIN_FAILURE.getMessage());
//             return "redirect:redirect:/home";
//         }

//         // ログイン成功の場合、権限、個人番号、セッションを設定する
//         redirectAttributes.addFlashAttribute("msg", CommonMessage.LOGIN_SUCCESS.getMessage());

//         accountDto.setAccountRole(Status.ACCOUNT_ROLE_USER.getStatusCode());
//         accountDto.setPersonalId(loginResult.getPersonalId());
//         sessionUtils.setSession(accountDto, request);

//         return "redirect:/user/dashboard";
//     }

//     /**
//      * 新しいユーザーを登録する。 作成者:INEXT_奥田
//      *
//      * @param accountDto         アカウントDTO
//      * @param mailSentMode       メール送信モード
//      * @param passwordCheck      パスワード確認
//      * @param redirectAttributes リダイレクト属性
//      * @return カテゴリー採番
//      * @throws MessagingException メール送信エラーが発生した場合にスローされます カテゴリー採番を取得する。
//      */
//     @PostMapping("/user/{mailSentMode}/mail")
//     public String signUpNewUser(AccountDto accountDto, @PathVariable("mailSentMode") String mailSentMode,
//             @Nullable @RequestParam("passwordCheck") String passwordCheck, RedirectAttributes redirectAttributes)
//             throws MessagingException {

//         // 権限を取得する
//         String userRole = Status.ACCOUNT_ROLE_USER.getStatusName();

//         // メール送信モードがアカウント登録メール送信の場合
//         if (mailSentMode.equals(MailSentMode.SENT_MODE_SIGNUP_MAIL.getModeName())) {
//             // パスワードチェックがnullの場合
//             if (passwordCheck == null) {
//                 // 必須入力が不足しているため、リダイレクトしてエラーメッセージを表示する
//                 redirectAttributes.addFlashAttribute("msg", CommonMessage.REQUIRED_INPUT_NULL.getMessage());
//                 return "redirect:/home";
//             }
//         }

//         // 送信したメールの結果を取得する
//         String mailSentResult = accountService.mailSend(accountDto, mailSentMode, passwordCheck, userRole);
//         redirectAttributes.addFlashAttribute("msg", mailSentResult);

//         return "redirect:/home";
//     }

//     /**
//      * ユーザーユニコードをチェックし、削除する。 作成者:INEXT_奥田
//      *
//      * @param unicode            ユニコード
//      * @param redirectAttributes リダイレクト先に渡す属性情報を保持するオブジェクト
//      * @return リダイレクト先のURL
//      */
//     @GetMapping("/user/signUp/check/{unicode}")
//     public String checkUserUnicode(@PathVariable("unicode") String unicode, RedirectAttributes redirectAttributes) {

//         // 権限を取得する
//         String userRole = Status.ACCOUNT_ROLE_MANAGER.getStatusName();

//         // ユニコードを編集し、結果を取得する
//         String editUnicodeResult = accountService.editUnicode(userRole, null, unicode, null);

//         redirectAttributes.addFlashAttribute("msg", editUnicodeResult);
//         return "redirect:/home";
//     }

// }
