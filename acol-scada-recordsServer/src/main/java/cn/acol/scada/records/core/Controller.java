package cn.acol.scada.records.core;


public interface Controller {
	/**
	 * 当收到上传记录时进行处理
	 * @param request
	 */
	public  void handler(Request request);
	/**
	 * 
	 * @param request 请求头
	 * @param responseInfo 已经有默认实现 如果还需要修改设置则可使用此设置
	 * 
	 */
	public  void handler(RequestHeader requestHeader, ResponseInfo responseInfo);
}
