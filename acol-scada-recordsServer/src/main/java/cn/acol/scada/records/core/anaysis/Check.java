package cn.acol.scada.records.core.anaysis;

public interface Check {
	/**
	  * 数据校验   一旦进入可确保此数据可以解析
	 * @param data  收到的数据
	 * @param len   数据长度
	 * @return   此数据为有效数据则返回true 无效返回false
	 */
	public boolean dataCheck(byte[] data, int len);
	
	/**
	 * @param data  数据
	 * @param len  校验有效数据的长度
	 * @return 在发送的数据中加入校验
	 */
	public void entryCheckCode(byte[] data);
}
