package cn.acol.scada.records.core;


/**
 * 采集器控制器  可升级采集器程序， 修改采集间隔  修改上传间隔，采集器时间，  以及一些报警设定等等
 */
public interface ScadaControl {
	
	/**
	 * 为采集器设置采集间隔 ， 单位毫秒
	 * @param ms
	 */
	public void setCollectionTimes(Long ms);
	/**
	 * 设置上传间隔， 单位ms
	 * @param ms
	 */
	public void setUpTimes(Long ms);
	/**
	 * 纠正采集器时间
	 */
	public void flushTimes();
	
	/**
	 * 获取命令
	 * @return
	 */
	public byte[] getControllerCommand();
	
	/**
	 * 设置单价
	 */
	public void setPrice(float price);
}
