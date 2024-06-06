package com.inext.manage_system.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Company {

    /** 協力会社ID **/
    private int corpId;

    /** 会社名：英語 **/
    private String corpEn;

    /** 会社名：日本語 **/
    private String corpJp;

    /** 会社名：中国語 **/
    private String corpCh;

}
