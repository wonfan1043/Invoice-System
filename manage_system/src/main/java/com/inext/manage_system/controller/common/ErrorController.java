package com.inext.manage_system.controller.common;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 作成者:INEXT_奥田 日付 2023/09/06
 */
@Controller
public class ErrorController {

    /**
     * セッションエラーページを表示する。 作成者:INEXT_奥田
     *
     * @param model モデルオブジェクト
     * @return セッションエラーページのビュー名
     */
    @GetMapping("/error/page/type/session")
    public String sessionError(Model model) {

        // エラーページ表示
        model.addAttribute("errorTitle", "Error : session error");
        return "/common/Error";
    }

    /**
     * 不正なURLエラーページを表示する。 作成者:INEXT_奥田
     *
     * @param model モデルオブジェクト
     * @return 不正なURLエラーページのビュー名
     */
    @GetMapping("/error/page/type/incorrectUrl")
    public String incorrectUrl(Model model) {

        // エラーページ表示
        model.addAttribute("errorTitle", "Error : incorrectUrl");
        return "/common/Error";
    }

    /**
     * ユーザーが見つからないエラーページを表示する。 作成者:INEXT_奥田
     *
     * @param model モデルオブジェクト
     * @return ユーザーが見つからないエラーページのビュー名
     */
    @GetMapping("/error/page/type/notFound")
    public String userNotFound(Model model) {

        // エラーページ表示
        model.addAttribute("errorTitle", "Error : Data Not Found");
        return "/common/Error";
    }

}
