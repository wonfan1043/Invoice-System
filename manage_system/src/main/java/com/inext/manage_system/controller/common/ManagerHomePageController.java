// package com.inext.manage_system.controller.common;

// import javax.mail.MessagingException;

// import org.springframework.lang.Nullable;
// import org.springframework.stereotype.Controller;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.servlet.mvc.support.RedirectAttributes;

// import com.inext.manage_system.common.SessionUtils;
// import com.inext.manage_system.dto.AccountDto;
// import com.inext.manage_system.enums.CommonMessage;
// import com.inext.manage_system.enums.Status;
// import com.inext.manage_system.service.account.AccountService;

// import jakarta.annotation.Resource;
// import jakarta.servlet.http.HttpServletRequest;

// /**
//  * 作成者:INEXT_奥田 日付 2023/09/01
//  */
// @Controller
// public class ManagerHomePageController {

//     @Resource
//     private AccountService accountService;
//     @Resource
//     private SessionUtils sessionUtils;

//     /**
//      * 管理画面を表示する。 作成者:INEXT_奥田
//      *
//      * @return 管理画面
//      */
//     @GetMapping("/manager/home")
//     public String homePage() {
//         // ホームページ表示
//         return "/common/ManagerHomePage";
//     }

//     /**
//      * ログイン処理を行います。 作成者:INEXT_奥田
//      *
//      * @param accountDto         アカウントDTO
//      * @param request            HTTPリクエストオブジェクト
//      * @param redirectAttributes リダイレクト先に渡す属性情報を保持するオブジェクト
//      * @return リダイレクト先のURL
//      */
//     @PostMapping("/manager/login")
//     public String login(AccountDto accountDto, HttpServletRequest request, RedirectAttributes redirectAttributes) {

//         String userRole = Status.ACCOUNT_ROLE_MANAGER.getStatusName();

//         // ログイン情報を取得
//         AccountDto selectAccountDto = accountService.checkLoginUserIsExist(accountDto, userRole);

//         if (selectAccountDto == null) {

//             // 情報がない場合、メッセージを提示する
//             redirectAttributes.addFlashAttribute("msg", CommonMessage.ERROR.getMessage());
//             return "redirect:/manager/home";
//         }

//         // ログイン成功の場合
//         redirectAttributes.addFlashAttribute("msg", CommonMessage.LOGIN_SUCCESS.getMessage());

//         // 権限、個人番号を設定する
//         accountDto.setAccountRole(Status.ACCOUNT_ROLE_MANAGER.getStatusCode());
//         accountDto.setPersonalId(selectAccountDto.getPersonalId());

//         // セッション設定
//         sessionUtils.setSession(accountDto, request);

//         return "redirect:/manager/dashboard";

//     }

//     /**
//      * 新しいユーザーを登録する。 作成者:INEXT_奥田
//      *
//      * @param accountDto         アカウントDTO
//      * @param mailSentMode       メール送信モード
//      * @param passwordCheck      パスワード確認
//      * @param redirectAttributes リダイレクト属性
//      * @return カテゴリー採番
//      * @throws MessagingException メール送信エラーが発生した場合にスローされます
//      */
//     @PostMapping("/manager/{mailSentMode}/mail")
//     public String signUpNewUser(AccountDto accountDto, @PathVariable("mailSentMode") String mailSentMode,
//             @Nullable @RequestParam("passwordCheck") String passwordCheck, RedirectAttributes redirectAttributes)
//             throws MessagingException {

//         // 権限を取得する
//         String userRole = Status.ACCOUNT_ROLE_MANAGER.getStatusName();

//         // 送信した結果を取得する
//         String mailSentResult = accountService.mailSend(accountDto, mailSentMode, passwordCheck, userRole);

//         redirectAttributes.addFlashAttribute("msg", mailSentResult);
//         return "redirect:/manager/home";
//     }

//     /**
//      * ユーザーのユニコードをチェックする。 作成者:INEXT_奥田
//      *
//      * @param unicode            ユニコード
//      * @param redirectAttributes リダイレクト先に渡す属性情報を保持するオブジェクト
//      * @return リダイレクト先のURL
//      */
//     @GetMapping("/manager/signUp/check/{unicode}")
//     public String checkUserUnicode(@PathVariable("unicode") String unicode, RedirectAttributes redirectAttributes) {

//         String userRole = Status.ACCOUNT_ROLE_MANAGER.getStatusName();

//         // ユニコードを削除結果
//         String editUnicode = accountService.editUnicode(userRole, null, unicode, null);
//         redirectAttributes.addFlashAttribute("msg", editUnicode);

//         return "redirect:/manager/home";
//     }

// }
