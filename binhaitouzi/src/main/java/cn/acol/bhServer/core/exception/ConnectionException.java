package cn.acol.bhServer.core.exception;

public class ConnectionException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3208094807793869094L;

	public ConnectionException(String msg, Exception e) {
		super("错误信息为： "+ msg + "异常信息： "+e.getMessage());
	}
	public ConnectionException(String msg) {
		super(msg);
	}
	

}
