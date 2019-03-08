package cn.acol.scada.records.core.anaysis;

import cn.acol.scada.records.utils.Utils;

public class AnaysisException extends Exception{

	private static final long serialVersionUID = 1644275539901232935L;
	
	public AnaysisException(byte[] b, int len,String msg) {
		super(msg + Utils.bytesToHexStringForWatch(b, len));
	}
	public AnaysisException(String msg) {
		super(msg);
	}
	public AnaysisException(String reason, byte[] data) {
		super(reason+":  "+Utils.bytesToHexStringForWatch(data, data.length));
	}
}
