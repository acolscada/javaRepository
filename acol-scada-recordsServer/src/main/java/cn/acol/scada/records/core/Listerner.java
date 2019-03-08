package cn.acol.scada.records.core;


/**
 * TCP/IP数据流的监听器 
 * 可为安全检测等工作服务
 * @author DaveZhang
 *
 */
public interface Listerner {
	/**
	 * 数据为socket 原始数据包  刚进入
	 * @param data 收到的协议层数据
	 * @param len  协议层数据长度
	 * @param session 整个回话的session
	 * throw 对数据来源的IP
	 */
	public void handler(byte[] data, int len,Session session) throws WarningException;
	
	
}
