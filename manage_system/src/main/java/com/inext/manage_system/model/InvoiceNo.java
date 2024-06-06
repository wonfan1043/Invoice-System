package com.inext.manage_system.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceNo {

    /** 番号 **/
    private int number;

    /** タイプコード **/
    private String type;

}
