package cn.acol.scada.records.core;



import cn.acol.scada.records.utils.Utils;
/**
 * 当数据异常时可抛出警告异常
 * @author DaveZhang
 *
 */
public class WarningException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7035766662531584762L;

	public WarningException(Session session, byte[] data, int len) {
		super("异常数据来自IP： "+session.getSocket().getInetAddress().getHostAddress()+"其端口号为： "+session.getSocket().getPort() + "异常数据为:  " + Utils.bytesToHexStringForWatch(data, len));
	}
}
