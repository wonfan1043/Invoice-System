package com.inext.manage_system.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

/**
 * 作成者:INEXT_奥田 日付 2023/09/06
 */
@Data
public class AccountDto {

    /** 個人番号 **/
    private String personalId;

    /** 苗字 **/
    private String firstName;

    /** 名前 **/
    private String lastName;

    /** 誕生日 **/
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    /** メール1 **/
    private String emailInternal;

    /** メール2 **/
    private String emailOther;

    /** 権限 **/
    private Integer accountRole;

    /** アカウント認証 **/
    private Integer accountVerification;

    /** 退職 **/
    private Integer resignation;

    /** パスワード **/
    private String password;

    /** ユニコード **/
    private String unicode;

    /** ユニコード效期 **/
    private LocalDateTime unicodeExpiresDate;

    /** ログイン情報 **/
    private LocalDateTime logInDate;

    /** ログアウト情報 **/
    private LocalDateTime logOutDate;

    /** ログイン有効期限 **/
    private LocalDateTime logInExpiresDate;

    /** ログインステータス **/
    private Integer logInStatus;

}