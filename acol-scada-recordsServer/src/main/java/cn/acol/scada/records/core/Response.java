package cn.acol.scada.records.core;

import java.io.IOException;

public abstract class Response {
	
	protected Session session;
	/**
	 * @param session 可以用于获取输出流
	 */
	public Response(Session session){
		this.session = session;
	}
	
	public void setSession(Session session) {
		this.session = session;
	}
	public Response() {
		
	}
	
	/**
	 * 设置采集间隔 不带提交
	 */
	public abstract void setCollectionTime(Long ms);
	/**
	 * 设置上传间隔 不带提交
	 */
	public abstract void setUpTime(Long ms);
	/** 
	 * 更新时间  不进行提交
	 */
	public abstract void flushTime();
	
	public abstract void setPrice(Float price);
	/**
	 * 确认上传成功，此方法代表确认收到了历史数据  并回应信息
	 * @throws ConnectionException 
	 */
	public abstract void absoluteUpSuccessful() throws ConnectionException;
	
	
	/**
	 * 提交
	 */
	public abstract void commit() throws ConnectionException;
	
	public void sendData(byte[] data) throws ConnectionException{
		try {
			session.getOutputStream().write(data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new ConnectionException(e.getMessage());
		}
	}
	public Session getSession() {
		return session;
	}
	}
