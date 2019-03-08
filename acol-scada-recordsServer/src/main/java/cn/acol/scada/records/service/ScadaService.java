package cn.acol.scada.records.service;


import cn.acol.scada.records.core.ResponseInfo;
import cn.acol.scada.records.core.Session;
import cn.acol.scada.records.domain.UpRecord;

public interface ScadaService{
	/**
	 * *    获得设备的当前状态
	 *  返回设备是否有注册 以及上传间隔，采集间隔， 单价等信息
	 * @param deviceNumber
	 * @param session 会话级别的缓存器
	 * @return
	 */
	public  ResponseInfo getDeviceStatus(String deviceNumber,Session session);
	
	/**
	 * 提交整个回话的最后一条记录
	 * @param lastUpRecord 整个回话中的最后一条上传记录
	 * @param deviceNumber 设备号
	 */
	public  void submitLastRecords(Session session);
	/**
	 * 跟新最后一条记录
	 * @param request
	 */
	public void flushLastRecords(UpRecord upRecord, Session session);
}
