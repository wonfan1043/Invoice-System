package com.inext.manage_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseRes {

	/** Common Message コード **/
    private int code;

	/** Common Message メッセージ**/
	private String message;

}
