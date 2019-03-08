package cn.acol.bhServer.core.exception;

public class AnaysisException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3601608243209071455L;
	
	public AnaysisException(String msg) {
		super("解析错误原因为： "+ msg);
	}

}
