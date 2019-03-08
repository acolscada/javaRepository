package cn.acol.scada.jituan.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 
 * @author DaveZhang
 *
 */
@Data
@AllArgsConstructor
public class JtRMessage {
	private int errorcode;
	private String errmsg;
}
