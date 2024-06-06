// package com.inext.manage_system.controller.common;

// import java.util.Objects;

// import org.apache.ibatis.annotations.Param;
// import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.servlet.mvc.support.RedirectAttributes;

// import com.inext.manage_system.enums.CommonMessage;
// import com.inext.manage_system.service.account.AccountService;

// import jakarta.annotation.Resource;

// /**
//  * 作成者:INEXT_奥田 日付 2023/09/12
//  */
// @Controller
// public class PasswordEditController {

//     @Resource
//     private AccountService accountService;

//     /**
//      * パスワードを編集する。 作成者:INEXT_奥田
//      *
//      * @param model    モデルオブジェクト
//      * @param mode     モード
//      * @param unicode  ユニコード
//      * @param userRole ユーザーロール
//      * @return パスワード編集画面のビュー名またはリダイレクト先のURL
//      */
//     @GetMapping("/{userRole}/{mode}/password/edit/{unicode}")
//     public String passwordEdit(Model model, @PathVariable("mode") String mode, @PathVariable("unicode") String unicode,
//             @PathVariable("userRole") String userRole) {

//         // 権限チェック
//         if (userRole == null) {
//             model.addAttribute("msg", CommonMessage.UNKNOWN_WEB_USER_ROLE.getMessage());
//             return "redirect:/error/page/type/incorrectUrl";
//         }

//         // モードチェック
//         if (mode == null) {

//             // 不正の場合、エラーメッセージを返す
//             model.addAttribute("msg", CommonMessage.UNKNOWN_MODE.getMessage());
//             return "redirect:/error/page/type/incorrectUrl";
//         }

//         // ユニコードのチェックした結果を取得する
//         String checkUnicodeIsExistResult = accountService.checkUnicodeIsExist(unicode);

//         // ユニコードが存在する場合
//         if (Objects.equals(checkUnicodeIsExistResult, CommonMessage.UNICODE_EXISTS.getMessage())) {

//             // モード、ユニコードを設定する
//             model.addAttribute("mode", mode);
//             model.addAttribute("unicode", unicode);

//             return "/common/PasswordEdit";
//         }

//         // ユニコードが存在しない場合はエラーメッセージを返す
//         model.addAttribute("msg", checkUnicodeIsExistResult);
//         return "redirect:/error/page/type/incorrectUrl";
//     }

//     /**
//      * ユーザーパスワードの編集を行います。 作成者:INEXT_奥田
//      *
//      * @param userRole           ユーザーロール
//      * @param redirectAttributes リダイレクト先に渡す属性情報を保持するオブジェクト
//      * @param passwordOld        古いパスワード
//      * @param password           新しいパスワード
//      * @param passwordCheck      パスワード確認
//      * @param unicode            ユニコード
//      * @return リダイレクト先のURL
//      */
//     @PostMapping("/{userRole}/password/edit/sent")
//     public String userPasswordEdit(@PathVariable("userRole") String userRole, RedirectAttributes redirectAttributes,
//             @Param("passwordOld") String passwordOld, @Param("password") String password,
//             @Param("passwordCheck") String passwordCheck, @Param("unicode") String unicode) {

//         // パスワードの更新ステータスを取得
//         String updateResult = accountService.updateUserPassword(userRole, passwordOld, password, passwordCheck,
//                 unicode);

//         // 不正の場合、エラーメッセージを返す
//         redirectAttributes.addFlashAttribute("msg", updateResult);
//         return "redirect:/home";
//     }

// }
