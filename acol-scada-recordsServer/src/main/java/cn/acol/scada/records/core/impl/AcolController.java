package cn.acol.scada.records.core.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.acol.scada.records.core.Controller;
import cn.acol.scada.records.core.Request;
import cn.acol.scada.records.core.RequestHeader;
import cn.acol.scada.records.core.ResponseInfo;
import cn.acol.scada.records.domain.UpRecord;
import cn.acol.scada.records.repository.RecordRepository;
import cn.acol.scada.records.service.ScadaService;

@Component
public class AcolController implements Controller{
	
	
	private static final Logger log = LoggerFactory.getLogger(AcolController.class);
	
	@Autowired
	private RecordRepository recordRepository;
	@Autowired
	private ScadaService scadaService;
	@Override
	public void handler(Request request) {
		// TODO Auto-generated method stub
		//是否存在
		ResponseInfo responseInfo = scadaService.getDeviceStatus(request.requestHeader().getScaNum(), request.getSession());
		
		if(responseInfo.isExistDevice()) {
			List<UpRecord> upRecord;
			try {
				upRecord = request.getUpRecord();
				for(UpRecord upRecord2 :upRecord) {
					recordRepository.save(upRecord2); //保存upRecord
				}
				//在会话中保存 最后一条记录
				if(upRecord != null &&upRecord.size()!=0) {
					scadaService.flushLastRecords(upRecord.get(upRecord.size()-1), request.getSession());
				}
			} catch (Exception e) {
				log.error("记录处理失败 错误为："+ e.getMessage());
				throw new RuntimeException();
			}
		}
	}

	@Override
	public void handler(RequestHeader requestHeader, ResponseInfo responseInfo) {
		// TODO Auto-generated method stub
		log.info("此为一个接口提供了一整套流程   你也可以设置这些参数      当前参数与值为： "+"设备号"+requestHeader.getScaNum()+"设备存在否："+ responseInfo.isExistDevice()+"当前采集间隔:"
				+ responseInfo.getCollectionTime()+"当前上传间隔："+responseInfo.getUpTime()+"当前单价"+responseInfo.getPrice()
				);
	}

}
