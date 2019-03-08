package cn.acol.scada.records.core.anaysis;

/**
 * 传输层数据专用加解密算法
 * @author DaveZhang
 *
 */
public interface Encryption {
	/**
	 * 
	 * @param data 加密数据
	 * @param len 加密数据段长度
	 * @return  解密后的数据 解密失败返回null
	 */
	byte[] decrpt(byte[] data, int len);
	
	/**
	 * 
	 * @param data
	 * @param len
	 * @return
	 */
	byte[] encrpt(byte[] data, int len);
}
