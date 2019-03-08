package cn.acol.scada.records.core;


import java.net.InetAddress;
import java.util.List;


import cn.acol.scada.records.core.anaysis.AnaysisException;
import cn.acol.scada.records.domain.UpRecord;


public abstract class Request {
	public static SocketData getRequestData() {
		return new SocketData(new byte[1024]);
	}
	protected RequestHeader requestHeader;
	protected Session session;
	
	public Request() {
		
	}
	public void setSession(Session session) {
		this.session = session;
	}
	
	public Request(Session session) {
		this.session = session;
	}
	
	public RequestHeader requestHeader() {
		return requestHeader;
	}
	/**
	 * 有效的请求 到达
	 * @return
	 * @throws ConnectionException 
	 */
	public abstract boolean isReached(byte[] data, int len) throws AnaysisException;
	
	/**
	 * 获取数据
	 * @return
	 */
	public abstract byte[] getData();

	/**
	 * 获取上传的记录, 失败抛出解析错误异常
	 * @return
	 * @throws AnaysisException
	 */
	public abstract List<UpRecord> getUpRecord() throws AnaysisException;
	
	public InetAddress getInetAddress() {
		return session.getSocket().getInetAddress();
	}
	public Session getSession() {
		return session;
	}
}
