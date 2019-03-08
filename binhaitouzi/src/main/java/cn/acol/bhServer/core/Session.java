package cn.acol.bhServer.core;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;


import cn.acol.bhServer.core.exception.ConnectionException;
import cn.acol.scada.core.dto.ScaParamsDto;


public class Session {
	private boolean isDestroyConnection = false;
	private Socket socket = null;
	private InputStream inputStream = null;
	private OutputStream outputStream = null;
	private Map<Object, Object> attribute = new HashMap<>();
	private Thread thread = null;//当前线程
	
	
	//手机卡
	private String ICCID;
	//电池电压
	private Float volt;
	
	public Map<Object, Object> getAttribute() {
		return attribute;
	}
	public void setAttribute(Map<Object, Object> attribute) {
		this.attribute = attribute;
	}
	public String getICCID() {
		return ICCID;
	}
	public void setICCID(String iCCID) {
		ICCID = iCCID;
	}
	public Float getVolt() {
		return volt;
	}
	public void setVolt(Float volt) {
		this.volt = volt;
	}
	public void setSocket(Socket socket) {
		this.socket = socket;
	}
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	public void setOutputStream(OutputStream outputStream) {
		this.outputStream = outputStream;
	}
	/**
	 * 回话域   
	 * @param key
	 * @param value
	 */
	public void setAttribute(Object key, Object value) {
		attribute.put(key, value);
	}
	public Object getAttribute(Object key) {
		return attribute.get(key);
	}
	
	public Session(Socket socket) {
		this.socket = socket;
	}
	public InputStream getInputStream() throws IOException {
		if(this.inputStream ==null) {
			inputStream = socket.getInputStream();
		}
		return inputStream;
	}
	
	
	public void setThread(Thread thread) {
		this.thread = thread;
	}
	public OutputStream getOutputStream() throws IOException {
		if(this.outputStream ==null) {
			outputStream = socket.getOutputStream();
		}
		return outputStream;
	}
	public Socket getSocket() {
		
		return socket;
	}
	public void sleep(long minutes) throws InterruptedException {
		Thread.sleep(1000*60*minutes);
	}
	
	public void destory() throws IOException {
		if(inputStream !=null) {
			inputStream.close();
			inputStream = null;
		}
		
		if(outputStream !=null) {
			outputStream.close();
			outputStream = null;
		}
		
		if(socket !=null) {
			socket.close();
			socket = null;
		}
		isDestroyConnection = true;
		if(thread !=null) {
			thread.interrupt();//中断线程
		}
	}
	public synchronized SocketData hasDataReached() throws ConnectionException {
		if(isDestroyConnection) {
			throw new ConnectionException("socket已经主动断开  不可能有数据到");
		}
		SocketData socketData = new SocketData(new byte[1500]);
		try {
			socketData.len = this.getInputStream().read(socketData.data);
			return socketData;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new ConnectionException("IO连接错误",e);
		}
	}
}

