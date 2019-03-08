package cn.acol.scada.records.core;

public class SocketData {
	public byte[] data;
	public int len;
	public SocketData(byte[] data) {
		this.data = data;
	}
	public byte[] getData() {
		return data;
	}
	public void setData(byte[] data) {
		this.data = data;
	}
	public int getLen() {
		return len;
	}
	public void setLen(int len) {
		this.len = len;
	}
	
}
