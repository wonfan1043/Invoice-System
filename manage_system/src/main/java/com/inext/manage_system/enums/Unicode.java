package com.inext.manage_system.enums;

import lombok.Getter;

/**
 * 作成者:INEXT_奥田 日付 2023/09/07
 */
@Getter
public enum Unicode {

    // パターン
    LOWERCASE_CHARS(1, "abcdefghijklmnopqrstuvwxyz"), // 小文字のアルファベット
    UPPERCASE_CHARS(2, "ABCDEFGHIJKLMNOPQRSTUVWXYZ"), // 大文字のアルファベット
    NUMBERS(3, "0123456789"), // 数字
    SPECIAL_CHARS(4, "!@#$%&()_-+=?"); // 特殊文字

    // コードID
    private final Integer id;

    // キャラクタータイプ
    private final String characters;

    Unicode(Integer code, String characters) {
        this.id = code;
        this.characters = characters;
    }

}
