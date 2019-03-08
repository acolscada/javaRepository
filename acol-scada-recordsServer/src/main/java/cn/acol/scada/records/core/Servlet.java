package cn.acol.scada.records.core;


public abstract class Servlet{
	/**
	 * 当没有数据可传时应当定期上传
	 * @param scaNum 采集器编号
	 * @param signal  信号强度
	 * @param version 硬件版本号
	 * @param response  发送器 
	 * @throws ConnectionException
	 */
	public abstract void initHandler(RequestHeader requestHeader, Response response);
	/**
	 * 上传记录时
	 * @param request 
	 * @param response
	 */
	public abstract void upRecordsHandler(Request request,Response response);
	
	
	/**
	 * 一个回话结束即最后一个请求处理完之后
	 * 时调用此方法
	 * @param session
	 */
	public abstract void afterLastRequestHandlered(Session session);
}
