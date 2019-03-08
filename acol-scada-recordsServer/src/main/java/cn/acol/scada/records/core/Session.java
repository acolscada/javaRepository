package cn.acol.scada.records.core;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;




/**
 * 每一此采集器从连接到断开  的整个回话
 * @author DaveZhang
 *
 */
public class Session{
	private Socket socket = null;
	private InputStream inputStream = null;
	private OutputStream outputStream = null;
	private Map<Object, Object> attribute = new HashMap<>();
	
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
	
	public OutputStream getOutputStream() throws IOException {
		if(this.outputStream ==null) {
			outputStream = socket.getOutputStream();
		}
		return outputStream;
	}
	public Socket getSocket() {
		
		return socket;
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
	}
	
	public SocketData hasDataReached() throws ConnectionException {
		SocketData requestData = Request.getRequestData();
		try {
			requestData.len = getInputStream().read(requestData.data);
			return requestData;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new ConnectionException("IO连接错误："+ e.getMessage());
		}
		
	}
	
}
