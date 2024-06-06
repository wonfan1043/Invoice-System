package com.inext.manage_system.enums;

public class AppConstants {

    // デフォルトページ番号
    public static final int DEFAULT_PAGE_NUM = 1;
    // 毎ページ最大表示するデータ数
    public static final int PAGE_SIZE = 7;

    // 桁数
    public enum DataLength {

        // 主旨のデータ
        TOPIC_ID(20),
        TOPIC_NAME(20),
        TOPIC_COMMENT(50),
    
        // 共通のデータ
        CREATER(20),
        UPDATER(20);
    
        private int length; // 長さ

        DataLength(int length) {
            this.length = length;
        }

        public int getLength() {
            return length;
        }

    }

    // 日付タイプ
    public enum DateType {

        // 作成日
        CREATE_DATE(1),
        // 請求日
        CHARGE_DATE(2);

        private int code; // タイプコード

        private DateType(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }

    }

}
