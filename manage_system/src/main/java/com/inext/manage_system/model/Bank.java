package com.inext.manage_system.model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bank {

    /** 銀行ID **/
    private int bankId;

    /** 協力会社ID **/
    private String corpId;

    /** 銀行地域 **/
    private String bankArea;

    /** 銀行名 **/
    private String bankName; 

    /** 支店名 **/
    private String branchName;

    /** 支店コード **/
    private int branchCode;

    /** Swift Code **/
    private String swiftCode;

    /** 銀行の郵便番号（台湾） **/
    private String twBankPostCode;

    /** 銀行の県（台湾） **/
    private String twBankCounty;

    /** 銀行の郡（台湾） **/
    private String twBankTown;

    /** 銀行の番地（台湾） **/
    private String twBankRoad;

    /** 銀行の住所（台湾） **/
    private String twBankAddress;

    /** 銀行のビル名（台湾） **/
    private String twBankHouseName;

    /** 銀行の郵便番号（日本） **/
    private String jpBankPostCode;

    /** 銀行の都道府県（日本） **/
    private String jpBankCounty;

    /** 銀行の市区町村（日本） **/
    private String jpBankTown;

    /** 銀行の番地（日本） **/
    private String jpBankAddress;

    /** 銀行のビル名（日本） **/
    private String jpBankHouseName;

    /** 口座名 **/
    private String accountName;

    /** 口座 **/
    private int accountNumber;

    /** 受取人の地域 **/
    private String receiveArea;

    /** 受取人の郵便番号（台湾） **/
    private String twReceivePostCode;

    /** 受取人の県（台湾） **/
    private String twReceiveCounty;

    /** 受取人の郡（台湾） **/
    private String twReceiveTown;

    /** 受取人の番地（台湾） **/
    private String twReceiveRoad;

    /** 受取人の住所（台湾） **/
    private String twReceiveAddress;

    /** 受取人のビル名（台湾） **/
    private String twReceiveHouseName;

    /** 受取人の郵便番号（日本） **/
    private String jpReceivePostCode;

    /** 受取人の都道府県（日本） **/
    private String jpReceiveCounty;

    /** 受取人の市区町村（日本） **/
    private String jpReceiveTown;

    /** 受取人の番地（日本） **/
    private String jpReceiveAddress;

    /** 受取人のビル名（日本） **/
    private String jpReceiveHouseName;

    /** 作成者 **/
    private String creator;

    /** 作成時間 **/
    private LocalDate createTime;

    /** 編集者 **/
    private String updator;

    /** 編集時間 **/
    private LocalDate updateTime;

    /** 削除フラグ **/
    private int delFlg;

}
