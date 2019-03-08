package cn.acol.bhServer.core.test;

import cn.acol.bhServer.core.Check;

public class CheckTest {
	private static Check check = Check.getInStance();
	private static byte[] data = new byte[] { 0x3C, 0x0B, 0x08, 0x15,
			0x05, 0x19,0x08,0x15,0x06,0x05,0x4b};
	public static void main(String[] args) {
		boolean dataCheck = check.dataCheck(data, data.length);
		System.out.println(dataCheck);
		check.entryCheckCode(data);
		System.out.println(data[data.length-1] +"   "+ data[data.length-2]);
	}
}
