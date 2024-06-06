package com.inext.manage_system.common;

import java.util.Objects;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.inext.manage_system.dto.AccountDto;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

/**
 * 作成者:INEXT_奥田 日付 2023/09/05
 */
@Service
public class SessionUtils {

    @Resource
    private Environment environment;

    /**
     * セッションを設定する。 作成者:INEXT_奥田
     *
     * @param accountDto アカウントDTO
     * @param request    HTTPリクエスト
     */
    public void setSession(AccountDto accountDto, HttpServletRequest request) {

        // 有効期限を取得する
        int sessionValidity = Integer
                .parseInt(Objects.requireNonNull(environment.getProperty("session.sessionValidity")));

        // ログイン情報にセッションを追加する
        HttpSession session = request.getSession(true);
        session.setAttribute("accountDto", accountDto);
        session.setMaxInactiveInterval(60 * sessionValidity);
    }

    /**
     * セッションを取得する。 作成者:INEXT_奥田
     *
     * @param request HTTPリクエスト
     * @return アカウントDTO
     */
    public AccountDto getSession(HttpServletRequest request) {

        HttpSession session = request.getSession(false);

        // セッションが存在しない場合、中止する
        if (session == null) {
            return null;
        }

        // セッションからユーザー情報を取得する
        AccountDto accountDto = (AccountDto) session.getAttribute("accountDto");

        // ユーザー情報がない場合、中止する
        if (accountDto == null || ObjectUtils.isEmpty(accountDto.getEmailInternal())
                || ObjectUtils.isEmpty(accountDto.getPassword())) {
            return null;
        }

        return accountDto;
    }

    /**
     * セッションをクリアする。 作成者:INEXT_奥田
     *
     * @param request HTTPリクエスト
     */
    public void clearSession(HttpServletRequest request) {

        // セッションをクリアする
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }
}
