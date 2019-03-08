package cn.acol.bhServer.core;

import cn.acol.bhServer.core.utils.CRCUtils;

public class Check {
	private static Check check = new Check();
	private Check() {}
	public static Check getInStance() {
		return check;
	}
	/**
	  * 数据校验   一旦进入可确保此数据可以解析
	 * @param data  收到的数据
	 * @param len   数据长度
	 * @return   此数据为有效数据则返回true 无效返回false
	 */
	public boolean dataCheck(byte[] data, int len) {
		if(data.length<5) {
			return false;
		}
		int calcCrc16 = CRCUtils.calcCrc16(data, 0, len-2);
		byte high = (byte) ((calcCrc16>>8)&0xff);
		byte low = (byte) (calcCrc16&0xff);
		if(high == data[len-2] && low == data[len-1]) {
			return true;
		}
		return false;
	}
	
	
	/**
	 * @param data  数据
	 * @param len  校验有效数据的长度
	 * @return 在发送的数据中加入校验
	 */
	public void entryCheckCode(byte[] data) {
		if(data.length<3) {
			return;
		}
		int calcCrc16 = CRCUtils.calcCrc16(data,0, data.length-2);
		byte high = (byte) ((calcCrc16>>8)&0x00ff);
		byte low = (byte) (calcCrc16&0x00ff);
		data[data.length-2] = high;
		data[data.length-1] = low;
		return;
	}
	
}
