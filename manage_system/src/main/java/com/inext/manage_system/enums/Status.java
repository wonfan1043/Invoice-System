package com.inext.manage_system.enums;

import lombok.Getter;

/**
 * 作成者:INEXT_奥田
 * 日付 2023/07/26
 */
@Getter
public enum Status {

    // 在職中の状態
    WORKING(1, "在職中"),
    // 退社の状態
    LEAVE(2, "退社"),

    // アカウント状態
    ACCOUNT_ROLE_MANAGER(1, "manager"),
    ACCOUNT_ROLE_USER(2, "user"),
    ACCOUNT_LOGOUT(1, "ログイン"),
    ACCOUNT_LOGIN(2, "ログアウト"),
    ACCOUNT_UN_VERIFICATION(1, "アカウントはまだ認証されていません"),
    ACCOUNT_VERIFICATION(2, "アカウントは認証です"),

    // ニュースの状態
    TOPIC_NEWS(1, "トップニュース"),
    NEWS(2, "トップニュース"),
    REMOVED_NEWS(3, "非表示ニュース"),

    // カテゴリー状態
    NORMAL_CATEGORY(1, "普通"),
    REMOVE_CATEGORY(2, "削除"),

    // サブカテゴリー状態設定
    NORMAL_SUB_CATEGORY(1, "普通"),
    REMOVE_SUB_CATEGORY(2, "削除");

    private final Integer statusCode; // 状態コード
    private final String statusName; // 状態名

    Status(Integer statusCode, String statusName) {
        this.statusCode = statusCode;
        this.statusName = statusName;
    }

}
