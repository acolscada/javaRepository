package cn.acol.bhServer.core;

import org.springframework.util.Assert;

public class SocketData {
	public byte[] data;
	public int len;
	public SocketData(byte[] data) throws IllegalArgumentException{
		
		Assert.notNull(data,"必须传入大于最大接受报文的数据域");
		this.data = data;
	}
}
