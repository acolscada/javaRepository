package cn.acol.bhServer.core.test;

import cn.acol.bhServer.core.exception.AnaysisException;
import cn.acol.bhServer.core.utils.Util;

public class UtilTest{
	public static final byte[] data = {0x40,0x40,0x31,0x30,0x36,0x34,0x38,0x31,0x32,0x33,0x34,0x35,0x36,0x37,0x38,0x49,0x50,0x3A,0x31,0x30,0x2E,0x36,0x36,0x2E,0x34,0x33,0x2E,0x32,0x32,0x23,0x23,0x33,0x33,0x0D};
	private static void bytesToStringTest(){
		
		System.out.println(Util.bytesToString(data, data.length));
		//System.out.println(data.length);
		
	}
	public static void main_2(String[] args) throws AnaysisException {
		   bytesToStringTest();
		
		//解析器测试
		/*SocketData socketData = new SocketData(new byte[1000]);
		socketData.data =data;
		socketData.len = data.length;
		Anaysis anaysis = new Anaysis(socketData);
		System.out.println(anaysis.getIccid() + "     "+ anaysis.getVolt());*/
		
	}
}
