package com.inext.manage_system.enums;

import lombok.Getter;

/**
 * 作成者:INEXT_奥田 日付 2023/07/18
 */
@Getter
public enum CommonMessage {

    // 成功メッセージ
    SEND_SUCCESS(1001, "送信に成功しました"), LOGIN_SUCCESS(1002, "ログインが成功しました"), SIGN_UP_SUCCESS(1003, "サインアップが成功しました"),
    PASSWORD_CHANGE_SUCCESS(1004, "パスワードを成功に変更しました"), UNICODE_EXISTS(1005, "Unicodeが存在しています"),
    REMOVE_SUCCESS(1006, "削除に成功しました"), LOGOUT_SUCCESS(1007, "ログアウトに成功しました"), UPDATE_SUCCESS(1008, "編集に成功しました"),
    INSERT_SUCCESS(1009, "登録に成功しました"),CREATE_SUCCESS(1010, "作成が成功しました"),PARAM_OKAY(1011,"入力内容に問題ありませんでした"),
    SUCCESS(1012, "成功しました"),
    // エラーメッセージ
    SEND_FAILURE(2001, "送信に失敗しました"), LOGIN_FAILURE(2002, "ログインに失敗しました。アカウントやパスワードを確認してください"),
    ACCOUNT_EXISTS(2003, "アカウントが既に存在しています"), ACCOUNT_NOT_EXISTS(2004, "アカウントが存在しません。正しいアカウントを入力してください"),
    OLD_PASSWORD_NOT_CHECK(2005, "変更に失敗しました。パスワードを確認してください"), PASSWORD_MISMATCH(2006, "パスワードが一致しません"),
    DATABASE_GET_NULL(2007, "データベースからデータを取得できませんでした"), UNKNOWN_MODE(2008, "不明なモードです。正しいモードを選択してください"),
    UNKNOWN_WEB_USER_ROLE(2010, "不明なユーザーロールです。正しいモードを選択してください"),
    ACCOUNT_UNVERIFIED(2011, "アカウントはまだ認証されていません。認証後にログインしてください"), UNKNOWN_UNICODE(2012, "Unicodeが見つかりません。メールを確認してください"),
    REMOVE_FAILURE(2013, "削除に失敗しました"), ID_NOT_FOUND(2014, "IDが見つかりません"), SESSION_ERROR(2015, "セッションエラー"),
    UPDATE_FAILURE(2016, "更新に失敗しました"), INSERT_FAILURE(2017, "登録に失敗しました"), EXPIRED_UNICODE(2018, "Unicodeの有効期限が切れています"),
    PARAM_ERROR(2019, "入力内容に誤りがありました"),DATA_TOO_BIG(2020, "桁数が制限に超えました"),TOPIC_ALREADY_EXISTS(2021, "主旨がすでに存在しています"),
    TOPIC_NOT_FOUND(2022,"主旨が見つかりませんでした"),INVOICE_ALREADY_EXISTS(2023, "請求書がすでに存在しています"),INVOICE_NOT_FOUND(2024,"請求書が見つかりませんでした"),
	SAMPLE_ALREADY_EXISTS(2025, "サンプルがすでに存在しています"),CREATE_INVOICE_FIRST(2026, "先に請求書を作成してください"),SAMPLE_NOT_FOUND(2027,"サンプルが見つかりませんでした"),
    COULD_NOT_DELETE_EXISTING_DATA(2028, "既存データの削除が失敗してしまいました"),NO_CREATER_INFO(2028, "作成者を入力してください"),NO_UPDATER_INFO(2029, "更新者を入力してください"),
    INVALID_TIME_RANGE(2030, "無効な時間帯です"),INVALID_INVOICE_NO(2031,"請求書番号を再確認してください。フォマード：英語大文字1桁と数字8桁"),

    // 注意メッセージ
    ERROR(3000, "エラーが発生しましたが、システムは何の操作も行われませんでした"), REQUIRED_INPUT_NULL(3001, "必須項目(*)が未入力です");

    private final Integer code;
    private final String message;

    CommonMessage(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
