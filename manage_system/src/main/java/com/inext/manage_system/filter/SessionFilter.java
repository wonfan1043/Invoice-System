package com.inext.manage_system.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.inext.manage_system.common.SessionUtils;
import com.inext.manage_system.dto.AccountDto;
import com.inext.manage_system.enums.Status;

import jakarta.annotation.Resource;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 作成者:INEXT_奥田 日付 2023/09/05
 */
@Component
@WebFilter(filterName = "SessionFilter", urlPatterns = "/*")
public class SessionFilter implements Filter {

    @Resource
    private SessionUtils sessionUtils;

    private final List<Pattern> userUrlPatterns = List.of(


            // "/manager/invoice/invoice_list"のURLパターンにマッチ
            Pattern.compile("^/manager/invoice/invoice_list$"),

            // "/manager/invoice/search_invoice"のURLパターンにマッチ
            Pattern.compile("^/manager/invoice/search_invoice$"),

            // "/manager/invoice/delete_invoice"のURLパターンにマッチ
            Pattern.compile("^/manager/invoice/delete_invoice$"),

            // "/manager/invoice/edit_invoice"のURLパターンにマッチ
            Pattern.compile("^/manager/invoice/edit_invoice$"),

            // "/manager/invoice/invoice_view"のURLパターンにマッチ
            Pattern.compile("^/manager/invoice/invoice_view?[^?]+$"),

            // "/manager/invoice/create_invoice"のURLパターンにマッチ
            Pattern.compile("^/manager/invoice/create_invoice$"),

            // "/manager/invoice/save_invoice"のURLパターンにマッチ
            Pattern.compile("^/manager/invoice/save_invoice$"),

            // "/manager/invoice/update_invoice"のURLパターンにマッチ
            Pattern.compile("^/manager/invoice/update_invoice$"),

            // "/manager/invoice/get_bank_info"のURLパターンにマッチ
            Pattern.compile("^/manager/invoice/get_bank_info$"),

            // "/manager/invoice/save_sample"のURLパターンにマッチ
            Pattern.compile("^/manager/invoice/save_sample$"),

            // "/manager/invoice/get_sample"のURLパターンにマッチ
            Pattern.compile("^/manager/invoice/get_sample?[^?]+$"),

            // "/manager/invoice/topic_list"のURLパターンにマッチ
            Pattern.compile("^/manager/invoice/topic_list$"),

            // "/manager/invoice/search_topic"のURLパターンにマッチ
            Pattern.compile("^/manager/invoice/search_topic$"),

            // "/manager/invoice/delete_topic"のURLパターンにマッチ
            Pattern.compile("^/manager/invoice/delete_topic$"),

            // "/manager/invoice/topic_list_change_page"のURLパターンにマッチ
            Pattern.compile("^/manager/invoice/topic_list/change_page$"),

            // "/manager/invoice/edit_topic"のURLパターンにマッチ
            Pattern.compile("^/manager/invoice/edit_topic?[^?]+$"),

            // "/manager/invoice/update_topic"のURLパターンにマッチ
            Pattern.compile("^/manager/invoice/update_topic$"),

            // "/manager/invoice/create_topic"のURLパターンにマッチ
            Pattern.compile("^/manager/invoice/create_topic$"),

            // "/manager/invoice/save_topic"のURLパターンにマッチ
            Pattern.compile("^/manager/invoice/save_topic$"),

            // ユーザーダッシュボード
            Pattern.compile("^/user/dashboard$"),

            // "/user/logout"のURLパターンにマッチ
            Pattern.compile("^/user/logout$"));

    private final List<Pattern> managerUrlPatterns = Arrays.asList(


            // "/manager/invoice/invoice_list"のURLパターンにマッチ
            Pattern.compile("^/manager/invoice/invoice_list$"),

            // "/manager/invoice/search_invoice"のURLパターンにマッチ
            Pattern.compile("^/manager/invoice/search_invoice$"),

            // "/manager/invoice/delete_invoice"のURLパターンにマッチ
            Pattern.compile("^/manager/invoice/delete_invoice$"),

            // "/manager/invoice/edit_invoice"のURLパターンにマッチ
            Pattern.compile("^/manager/invoice/edit_invoice$"),

            // "/manager/invoice/invoice_view"のURLパターンにマッチ
            Pattern.compile("^/manager/invoice/invoice_view?[^?]+$"),

            // "/manager/invoice/create_invoice"のURLパターンにマッチ
            Pattern.compile("^/manager/invoice/create_invoice$"),

            // "/manager/invoice/save_invoice"のURLパターンにマッチ
            Pattern.compile("^/manager/invoice/save_invoice$"),

            // "/manager/invoice/update_invoice"のURLパターンにマッチ
            Pattern.compile("^/manager/invoice/update_invoice$"),

            // "/manager/invoice/get_bank_info"のURLパターンにマッチ
            Pattern.compile("^/manager/invoice/get_bank_info$"),

            // "/manager/invoice/save_sample"のURLパターンにマッチ
            Pattern.compile("^/manager/invoice/save_sample$"),

            // "/manager/invoice/get_sample"のURLパターンにマッチ
            Pattern.compile("^/manager/invoice/get_sample?[^?]+$"),

            // "/manager/invoice/topic_list"のURLパターンにマッチ
            Pattern.compile("^/manager/invoice/topic_list$"),

            // "/manager/invoice/search_topic"のURLパターンにマッチ
            Pattern.compile("^/manager/invoice/search_topic$"),

            // "/manager/invoice/delete_topic"のURLパターンにマッチ
            Pattern.compile("^/manager/invoice/delete_topic$"),

            // "/manager/invoice/topic_list_change_page"のURLパターンにマッチ
            Pattern.compile("^/manager/invoice/topic_list/change_page$"),

            // "/manager/invoice/edit_topic"のURLパターンにマッチ
            Pattern.compile("^/manager/invoice/edit_topic?[^?]+$"),

            // "/manager/invoice/update_topic"のURLパターンにマッチ
            Pattern.compile("^/manager/invoice/update_topic$"),

            // "/manager/invoice/create_topic"のURLパターンにマッチ
            Pattern.compile("^/manager/invoice/create_topic$"),

            // "/manager/invoice/save_topic"のURLパターンにマッチ
            Pattern.compile("^/manager/invoice/save_topic$"),

            // "manager/category"のURLパターンにマッチ
            Pattern.compile("^/manager/category$"),

            // "manager/Category/add"のURLパターンにマッチ
            Pattern.compile("^/manager/Category/add$"),

            // "manager/category/rename"のURLパターンにマッチ
            Pattern.compile("^/manager/category/rename$"),

            // "manager/delete/category/{任意の文字列}"のURLパターンにマッチ
            Pattern.compile("^/manager/delete/category/[^/]+$"),

            // "manager/dashboard"のURLパターンにマッチ
            Pattern.compile("^/manager/dashboard$"),

            // "manager/logout"のURLパターンにマッチ
            Pattern.compile("^/manager/logout$"),

            // "manager/news/edit/{任意の文字列}"のURLパターンにマッチ
            Pattern.compile("^/manager/news/edit/[^/]+$"),

            // "manager/news/add"のURLパターンにマッチ
            Pattern.compile("^/manager/news/add$"),

            // "manager/news/insertNews"のURLパターンにマッチ
            Pattern.compile("^/manager/news/insertNews$"),

            // "manager/news/updateNews"のURLパターンにマッチ
            Pattern.compile("^/manager/news/updateNews$"),

            // "manager/newsList"のURLパターンにマッチ
            Pattern.compile("^/manager/newsList$"),

            // "manager/news/delete/{任意の文字列}"のURLパターンにマッチ
            Pattern.compile("^/manager/news/delete/[^/]+$"),

            // "staff/{任意の文字列}/Info"のURLパターンにマッチ
            Pattern.compile("^/staff/[^/]+/Info$"),

            // "manager/personalList"のURLパターンにマッチ
            Pattern.compile("^/manager/personalList$"),

            // "manager/category/{任意の文字列}/subCategory"のURLパターンにマッチ
            Pattern.compile("^/manager/category/[^/]+/subCategory$"),

            // "manager/category/subcategory/addSubCategory"のURLパターンにマッチ
            Pattern.compile("^/manager/category/subcategory/addSubCategory$"),

            // "manager/category/subcategory/editSubCategory"のURLパターンにマッチ
            Pattern.compile("^/manager/category/subcategory/editSubCategory$"),

            // "manager/delete/{任意の文字列}/{任意の文字列}"のURLパターンにマッチ
            Pattern.compile("^/manager/delete/[^/]+/[^/]+$"),

            // "/manager/news/removeNewsList"のURLパターンにマッチ
            Pattern.compile("^/manager/news/bulkRemoveNews$"),

            // "/manager/delete/removeCategories"のURLパターンにマッチ
            Pattern.compile("^/manager/delete/bulkRemoveCategory$"),

            // "/manager/delete/removeSubCategories"のURLパターンにマッチ
            Pattern.compile("^/manager/delete/bulkRemoveSubCategory$"));

    private final List<Pattern> commonUrlPatterns = Arrays.asList(

            // "/manager/invoice/invoice_list"のURLパターンにマッチ
            Pattern.compile("^/manager/invoice/invoice_list$"),

            // "/manager/invoice/search_invoice"のURLパターンにマッチ
            Pattern.compile("^/manager/invoice/search_invoice$"),

            // "/manager/invoice/delete_invoice"のURLパターンにマッチ
            Pattern.compile("^/manager/invoice/delete_invoice$"),

            // "/manager/invoice/edit_invoice"のURLパターンにマッチ
            Pattern.compile("^/manager/invoice/edit_invoice$"),

            // "/manager/invoice/invoice_view"のURLパターンにマッチ
            Pattern.compile("^/manager/invoice/invoice_view?[^?]+$"),

            // "/manager/invoice/create_invoice"のURLパターンにマッチ
            Pattern.compile("^/manager/invoice/create_invoice$"),

            // "/manager/invoice/save_invoice"のURLパターンにマッチ
            Pattern.compile("^/manager/invoice/save_invoice$"),

            // "/manager/invoice/update_invoice"のURLパターンにマッチ
            Pattern.compile("^/manager/invoice/update_invoice$"),

            // "/manager/invoice/get_bank_info"のURLパターンにマッチ
            Pattern.compile("^/manager/invoice/get_bank_info$"),

            // "/manager/invoice/save_sample"のURLパターンにマッチ
            Pattern.compile("^/manager/invoice/save_sample$"),

            // "/manager/invoice/get_sample"のURLパターンにマッチ
            Pattern.compile("^/manager/invoice/get_sample?[^?]+$"),

            // "/manager/invoice/topic_list"のURLパターンにマッチ
            Pattern.compile("^/manager/invoice/topic_list$"),

            // "/manager/invoice/search_topic"のURLパターンにマッチ
            Pattern.compile("^/manager/invoice/search_topic$"),

            // "/manager/invoice/delete_topic"のURLパターンにマッチ
            Pattern.compile("^/manager/invoice/delete_topic$"),

            // "/manager/invoice/topic_list_change_page"のURLパターンにマッチ
            Pattern.compile("^/manager/invoice/topic_list/change_page$"),

            // "/manager/invoice/edit_topic"のURLパターンにマッチ
            Pattern.compile("^/manager/invoice/edit_topic?[^?]+$"),

            // "/manager/invoice/update_topic"のURLパターンにマッチ
            Pattern.compile("^/manager/invoice/update_topic$"),

            // "/manager/invoice/create_topic"のURLパターンにマッチ
            Pattern.compile("^/manager/invoice/create_topic$"),

            // "/manager/invoice/save_topic"のURLパターンにマッチ
            Pattern.compile("^/manager/invoice/save_topic$"),

            // "staff/personal/create"のURLパターンにマッチ
            Pattern.compile("^/staff/personal/create$"),

            // "staff/personal/creating"のURLパターンにマッチ
            Pattern.compile("^/staff/personal/creating$"),

            // "staff/{任意の文字列}/BasicUpdate"のURLパターンにマッチ
            Pattern.compile("^/staff/[^/]+/BasicUpdate$"),

            // "staff/{任意の文字列}/SchoolUpdate"のURLパターンにマッチ
            Pattern.compile("^/staff/[^/]+/SchoolUpdate$"),

            // "staff/{任意の文字列}/WorkUpdate"のURLパターンにマッチ
            Pattern.compile("^/staff/[^/]+/WorkUpdate$"),

            // "staff/{任意の文字列}/ProjectUpdate"のURLパターンにマッチ
            Pattern.compile("^/staff/[^/]+/ProjectUpdate$"),

            // "staff/{任意の文字列}/LicenseUpdate"のURLパターンにマッチ
            Pattern.compile("^/staff/[^/]+/LicenseUpdate$"),

            // "staff/{任意の文字列}/ProgrammingUpdate"のURLパターンにマッチ
            Pattern.compile("^/staff/[^/]+/ProgrammingUpdate$"),

            // "staff/{任意の文字列}/LanguageUpdate"のURLパターンにマッチ
            Pattern.compile("^/staff/[^/]+/LanguageUpdate$"),

            // "staff/{任意の文字列}/IncentiveUpdate"のURLパターンにマッチ
            Pattern.compile("^/staff/[^/]+/IncentiveUpdate$"));

    private final List<Pattern> urlPassPattern = Arrays.asList(


            // "/manager/invoice/invoice_list"のURLパターンにマッチ
            Pattern.compile("^/manager/invoice/invoice_list$"),

            // "/manager/invoice/search_invoice"のURLパターンにマッチ
            Pattern.compile("^/manager/invoice/search_invoice$"),

            // "/manager/invoice/delete_invoice"のURLパターンにマッチ
            Pattern.compile("^/manager/invoice/delete_invoice$"),

            // "/manager/invoice/edit_invoice"のURLパターンにマッチ
            Pattern.compile("^/manager/invoice/edit_invoice$"),

            // "/manager/invoice/invoice_view"のURLパターンにマッチ
            Pattern.compile("^/manager/invoice/invoice_view?[^?]+$"),

            // "/manager/invoice/create_invoice"のURLパターンにマッチ
            Pattern.compile("^/manager/invoice/create_invoice$"),

            // "/manager/invoice/save_invoice"のURLパターンにマッチ
            Pattern.compile("^/manager/invoice/save_invoice$"),

            // "/manager/invoice/update_invoice"のURLパターンにマッチ
            Pattern.compile("^/manager/invoice/update_invoice$"),

            // "/manager/invoice/get_bank_info"のURLパターンにマッチ
            Pattern.compile("^/manager/invoice/get_bank_info$"),

            // "/manager/invoice/save_sample"のURLパターンにマッチ
            Pattern.compile("^/manager/invoice/save_sample$"),

            // "/manager/invoice/get_sample"のURLパターンにマッチ
            Pattern.compile("^/manager/invoice/get_sample?[^?]+$"),

            // "/manager/invoice/topic_list"のURLパターンにマッチ
            Pattern.compile("^/manager/invoice/topic_list$"),

            // "/manager/invoice/search_topic"のURLパターンにマッチ
            Pattern.compile("^/manager/invoice/search_topic$"),

            // "/manager/invoice/delete_topic"のURLパターンにマッチ
            Pattern.compile("^/manager/invoice/delete_topic$"),

            // "/manager/invoice/topic_list_change_page"のURLパターンにマッチ
            Pattern.compile("^/manager/invoice/topic_list/change_page$"),

            // "/manager/invoice/edit_topic"のURLパターンにマッチ
            Pattern.compile("^/manager/invoice/edit_topic?[^?]+$"),

            // "/manager/invoice/update_topic"のURLパターンにマッチ
            Pattern.compile("^/manager/invoice/update_topic$"),

            // "/manager/invoice/create_topic"のURLパターンにマッチ
            Pattern.compile("^/manager/invoice/create_topic$"),

            // "/manager/invoice/save_topic"のURLパターンにマッチ
            Pattern.compile("^/manager/invoice/save_topic$"),

            // "/{任意の文字列}/{任意の文字列}/password/edit/{任意の文字列}"のURLパターンにマッチ
            Pattern.compile("^/[^/]+/[^/]+/password/edit/[^/]+$"),

            // "/{任意の文字列}/password/edit/sent"のURLパターンにマッチ
            Pattern.compile("^/[^/]+/password/edit/sent$"),

            // "/home"のURLパターンにマッチ
            Pattern.compile("^/home$"),

            // "user/signUp/check/{任意の文字列}"のURLパターンにマッチ
            Pattern.compile("^/[^/]+/signUp/check/[^/]+$"),

            // "/user/login"のURLパターンにマッチ
            Pattern.compile("^/user/login$"),

            // "/manager/login"のURLパターンにマッチ
            Pattern.compile("^/manager/login$"),

            // "/{任意の文字列}/{任意の文字列}/mail"のURLパターンにマッチ
            Pattern.compile("^/[^/]+/[^/]+/mail$"),

            // "manager/home"のURLパターンにマッチ
            Pattern.compile("^/manager/home$"),

            // "/error/page/type/{任意の文字列}"のURLパターンにマッチ
            Pattern.compile("^/error/page/type/[^/]+$"),

            // "/newsList"のURLパターンにマッチ
            Pattern.compile("^/newsList$"),

            // "/newsList/posted/{任意の文字列}"のURLパターンにマッチ
            Pattern.compile("^/newsList/posted/[^/]+$"),

            // ↓↓↓↓静的リソース
            // /js/ディレクトリ名/ファイル名にマッチ
            Pattern.compile("^/js/[^/]+/[^/]+"),

            // /css/ディレクトリ名/ファイル名にマッチ
            Pattern.compile("^/css/[^/]+/[^/]+"),

            // /img/ディレクトリ名/ファイル名にマッチ
            Pattern.compile("^/img/[^/]+/[^/]+"),

            // /css/ディレクトリ名/ファイル名にマッチ
            Pattern.compile("^/css/[^/]+/[^/]+"),

            // /vendor/ディレクトリ名/ファイル名にマッチ
            Pattern.compile("^/vendor/[^/]+/[^/]+"),

            // /js/ディレクトリ名にマッチ
            Pattern.compile("^/js/[^/]+"),

            // /css/ディレクトリ名にマッチ
            Pattern.compile("^/css/[^/]+"),

            // /img/ディレクトリ名にマッチ
            Pattern.compile("^/img/[^/]+"),

            // /css/ディレクトリ名にマッチ
            Pattern.compile("^/css/[^/]+"),

            // /vendor/ディレクトリ名にマッチ
            Pattern.compile("^/vendor/[^/]+"));

    /**
     * フィルタを初期化する。 作成者:INEXT_奥田
     *
     * @param filterConfig フィルタの設定情報を保持するオブジェクト
     * @throws ServletException フィルタの初期化中に発生した例外
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    /**
     * フィルタを適用する。 作成者:INEXT_奥田
     *
     * @param servletRequest  サーブレットリクエスト
     * @param servletResponse サーブレットレスポンス
     * @param filterChain     フィルタチェイン
     * @throws IOException      入出力例外が発生した場合
     * @throws ServletException サーブレット例外が発生した場合
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String requestUrl = request.getRequestURI();

        if (ifContains(urlPassPattern, requestUrl)) {

            // フィルターパターンに一致する場合、フィルターチェーンを実行する
            filterChain.doFilter(request, response);

        } else if (ifMatches(userUrlPatterns, requestUrl)) {

            // ユーザーパターンに一致する場合、ログイン状態を確認する
            AccountDto accountDto = sessionUtils.getSession(request);

            if (accountDto == null) {

                // アカウントDTOが存在しない場合、セッションエラーページにリダイレクトする
                response.sendRedirect("/error/page/type/session");
            } else if (!accountDto.getAccountRole().equals(Status.ACCOUNT_ROLE_USER.getStatusCode())) {
                // アカウントの役割がユーザーではない場合、403 Forbiddenエラーを返する
                response.sendError(HttpStatus.FORBIDDEN.value());
            } else {
                // フィルターチェーンを実行する
                filterChain.doFilter(request, response);
            }
        } else if (ifMatches(managerUrlPatterns, requestUrl)) {
            // マネージャーパターンに一致する場合、ログイン状態を確認する
            AccountDto accountDto = sessionUtils.getSession(request);
            if (accountDto == null) {
                // アカウントDTOが存在しない場合、セッションエラーページにリダイレクトする
                response.sendRedirect("/error/page/type/session");
            } else if (!accountDto.getAccountRole().equals(Status.ACCOUNT_ROLE_MANAGER.getStatusCode())) {
                // アカウントの役割がマネージャーではない場合、403 Forbiddenエラーを返する
                response.sendError(HttpStatus.FORBIDDEN.value());
            } else {
                // フィルターチェーンを実行する
                filterChain.doFilter(request, response);
            }
        } else if (ifMatches(commonUrlPatterns, requestUrl)) {
            // 共通パターンに一致する場合、ログイン状態を確認する
            AccountDto accountDto = sessionUtils.getSession(request);
            if (accountDto == null) {
                // アカウントDTOが存在しない場合、セッションエラーページにリダイレクトする
                response.sendError(HttpStatus.FORBIDDEN.value());
            }
            // フィルターチェーンを実行する
            filterChain.doFilter(request, response);
        } else {
            // リクエストURLがどのパターンにも一致しない場合、404 Not Foundエラーを返する
            response.sendError(HttpStatus.NOT_FOUND.value());
        }
    }

    /**
     * 指定されたURLリストに、リクエストURLが含まれているかどうかを判定する。 作成者:INEXT_奥田
     *
     * @param urlList    URLリスト
     * @param requestUrl リクエストURL
     * @return リクエストURLがURLリストに含まれている場合はtrue、それ以外の場合はfalse
     */
    private boolean ifContains(List<Pattern> urlList, String requestUrl) {
        for (Pattern url : urlList) {
            if (requestUrl.matches(String.valueOf(url))) {
                return true;
            }
        }
        return false;
    }

    /**
     * 指定されたパターンリストに、リクエストURLがマッチするかどうかを判定する。 作成者:INEXT_奥田
     *
     * @param patternList パターンリスト
     * @param requestUrl  リクエストURL
     * @return リクエストURLがパターンリストにマッチする場合はtrue、それ以外の場合はfalse
     */
    private boolean ifMatches(List<Pattern> patternList, String requestUrl) {
        for (Pattern pattern : patternList) {
            if (pattern.matcher(requestUrl).matches()) {
                return true;
            }
        }
        return false;
    }

    /**
     * フィルタを破棄する。 作成者:INEXT_奥田
     *
     */
    @Override
    public void destroy() {
        Filter.super.destroy();
    }

}