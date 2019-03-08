package cn.acol.scada.records.core.impl;



import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import cn.acol.scada.records.core.ConnectionException;
import cn.acol.scada.records.core.Controller;
import cn.acol.scada.records.core.Request;
import cn.acol.scada.records.core.RequestHeader;
import cn.acol.scada.records.core.Response;
import cn.acol.scada.records.core.ResponseInfo;
import cn.acol.scada.records.core.Servlet;
import cn.acol.scada.records.core.Session;
import cn.acol.scada.records.service.ScadaService;


/**
 * 对于ACOL协议的服务程序
 * request 已完成封装
 * response 已完成常规协议功能的实现
 * 只需在 此类中实现代码逻辑方可
 */
@Component
public class AcolServlet extends Servlet{
	Logger logger = LoggerFactory.getLogger(AcolServlet.class);
	
	@Autowired
	private Controller controller;
	
	@Autowired
	private ScadaService scadaService;
	
	private boolean isSetTime(Long time) {
		if( (time!= null )&& (time.longValue() != 0)) {
			return true;
		}
		return false;
	}
	
	@Override
	public void initHandler(RequestHeader requestHeader, Response response){
		/**
		 * 越早知道设备号参数越好
		 * 而Controller中ResponseInfo 无法拿到会话域 故在此实现
		 * 查看是否有该设备
		 */
		ResponseInfo responseInfo = scadaService.getDeviceStatus(requestHeader.getScaNum(), response.getSession());
		//获取参数信息  参数信息进行预处理
		controller.handler(requestHeader, responseInfo);
		Long collectionTime = responseInfo.getCollectionTime();
		if(isSetTime(collectionTime)) {
			response.setCollectionTime(collectionTime);
		}
		Long upTime = responseInfo.getUpTime();
		if(isSetTime(upTime)) {
			response.setUpTime(upTime);
		}
		Float price = responseInfo.getPrice();
		if(price!=null && price.equals(Float.valueOf(0))) {
			response.setPrice(price);
		}
		response.flushTime();
		try {
			response.commit();
		} catch (ConnectionException e) {
			// TODO Auto-generated catch block
			logger.error("发送控制命令失败");
		}
	}
	@Override
	public void upRecordsHandler(Request request, Response response) {
		// TODO Auto-generated method stub
		if(!scadaService.getDeviceStatus(request.requestHeader().getScaNum(),request.getSession()).isExistDevice()) {
			try {
				request.getSession().destory();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				return;
			}
		}
		controller.handler(request);
		try {
			response.absoluteUpSuccessful();
		} catch (ConnectionException e) {
			logger.error("回应历史数据失败: "+ e.getMessage());
		}
	}
	@Override
	public void afterLastRequestHandlered(Session session) {
		// TODO Auto-generated method stub
		//处理完所有请求后 需要进行的操作
		scadaService.submitLastRecords(session);
		
	}

}
