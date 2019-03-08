package cn.acol.bhServer.core.utils;

import cn.acol.bhServer.core.exception.AnaysisException;

public class Util {
	public static String bytesToString(byte[] data,int len) {
		 StringBuffer  buffer=new StringBuffer ();

	      char[] tChars=new char[len];
	      
	      for(int i=0;i<len;i++) 
	       tChars[i]=(char)data[i];
	      
	      buffer.append(tChars);
	      
	      return buffer.toString();
	}
	private static byte charToByte(char c) {
	    return (byte) "0123456789ABCDEF".indexOf(c);
	}
	public static byte[] hexStringToBytes(String hexString) {
		if (hexString == null || hexString.equals("")) {
			return null;
		}
		hexString = hexString.toUpperCase();
		int length = hexString.length() / 2;
		char[] hexChars = hexString.toCharArray();
		byte[] d = new byte[length];
		for (int i = 0; i < length; i++) {
			int pos = i * 2;
			d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
		}
		return d;
	}
	public static void main(String[] args) {
		String string = "48290472472897423892";
		byte[] a = hexStringToBytes(string);
		for(int i=0;i<a.length;i++) {
			string =string + Integer.toHexString(a[i]&0xff)+ "  ";
		}
		System.out.println(string);
	}
	
	
	public static void putLongIntoBytes(long l, byte[] bs, int offset) throws AnaysisException {
		if(bs == null || bs.length<offset+8 ) {
			throw new AnaysisException("需要8字节的长度");
		}
		if(offset<0) {
			throw new AnaysisException("偏移不能为负数");
		}
		for(int i = offset;i<offset+8;i++) {
			int mid = 64 - (i+1-offset) * 8;
			bs[i] = (byte) ((l >> mid) & 0xff);
		}
	}
	public static void putIntIntoBytes(int data, byte[] bs, int offset) throws AnaysisException {
		if(bs == null || bs.length<offset+4 ) {
			throw new AnaysisException("需要4字节的长度");
		}
		if(offset<0) {
			throw new AnaysisException("偏移不能为负数");
		}
		for(int i = offset;i<offset+4;i++) {
			int mid = 32 - (i+1-offset) * 8;
			bs[i] = (byte) ((data >> mid) & 0xff);
		}
	}
}

