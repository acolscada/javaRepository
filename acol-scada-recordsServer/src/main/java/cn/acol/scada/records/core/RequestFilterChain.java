package cn.acol.scada.records.core;

import cn.acol.scada.records.core.anaysis.AnaysisException;

/**
 * 采集器协议流 数据的过滤链
 * 请求过滤链  进行字段检测安全校验等信息
 * 
 * @author DaveZhang
 *
 */
public abstract class RequestFilterChain {
	private RequestFilterChain nextFilterChain = null;
	
	public void setNextFilter(RequestFilterChain nextAnaysisFilterChain) {
		this.nextFilterChain = nextAnaysisFilterChain;
	}
	
	/**
	 *socket 刚进来时的原始报文进行监听与处理
	 * @param data 接收到的数据
	 * @param len  接收到数据的长度
	 * @throws AnaysisException 如果数据报文错误则直接抛出AnaysisException
	 */
	abstract protected void receivedHandle(byte[] data, int len)throws AnaysisException;
	
	public RequestFilterChain nextFilterChain() {
		return nextFilterChain;
	}
	public void addFilterToEnd(RequestFilterChain afc) {
		RequestFilterChain preAnaysisFilterChain = this;
		RequestFilterChain nowAnaysisFilterChain = preAnaysisFilterChain;
		while(nowAnaysisFilterChain!=null) {
			preAnaysisFilterChain = nowAnaysisFilterChain;
			nowAnaysisFilterChain = nowAnaysisFilterChain.nextFilterChain;
		}
		preAnaysisFilterChain.setNextFilter(afc);
	}
	public void doFilter(byte[] data,int len) throws AnaysisException {
		this.receivedHandle(data, len);
		if(nextFilterChain!=null) {
			nextFilterChain.doFilter(data, len);
		}
	}
}
