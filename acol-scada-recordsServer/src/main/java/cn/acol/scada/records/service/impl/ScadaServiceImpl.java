package cn.acol.scada.records.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import cn.acol.scada.core.dto.LastRecordDto;
import cn.acol.scada.core.dto.ScaParamsDto;
import cn.acol.scada.core.dto.SimpleResponse;
import cn.acol.scada.core.utils.DiscoveryUtil;
import cn.acol.scada.records.core.ResponseInfo;
import cn.acol.scada.records.core.Session;
import cn.acol.scada.records.domain.UpRecord;
import cn.acol.scada.records.service.ScadaService;

@Component
public class ScadaServiceImpl implements ScadaService{

	private static final Logger log = LoggerFactory.getLogger(ScadaServiceImpl.class);

	//private static final String SCAEXIST = "EXIST_KEY"; //用于保存一个回话中采集器是否存在
	private static final String DEVICEINFO = "DEVICEINFO_KEY"; //用于会话中设备信息的主键
	//用于与其他应用之间 上传最后一条消息并确认采集器是否整的存在 
	
	private static final String LASTRECORD = "LASTRECORD_KEY";
	
	@Autowired
	private DiscoveryUtil discoveryUtil;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Override
	public ResponseInfo getDeviceStatus(String deviceNumber, Session session) {
		// TODO Auto-generated method stub
		ResponseInfo responseInfo = (ResponseInfo) session.getAttribute(DEVICEINFO);
		if(responseInfo !=null) {
			return responseInfo;
		}
		responseInfo = new ResponseInfo();
		try {
			ScaParamsDto scaParams = restTemplate.getForObject(discoveryUtil.getAcolScadaManageUrl()+"/device/"+deviceNumber+"/scaParams", ScaParamsDto.class);
			
			if(scaParams !=null) {
				responseInfo.setExistDevice(true);
				responseInfo.setCollectionMinTime(scaParams.getDesColTime());
				responseInfo.setUpMinTime(scaParams.getDesUpTime());
				if(scaParams.getColTime() != 60 || scaParams.getUpTime() !=1440) {
					scaParams.setColTime(60);
					scaParams.setUpTime(1440);
					scaParams.setDeviceNum(deviceNumber);
					restTemplate.postForObject(discoveryUtil.getAcolScadaManageUrl()+"/device/"+deviceNumber+"/scaParams", scaParams, SimpleResponse.class);//getForObject(t, responseType)
				}
				//responseInfo.setPrice(scaParams.getPrice());
			}else {
				/**
				 * 默认参数配置
				 */
				responseInfo.setExistDevice(false);
				responseInfo.setCollectionMinTime(60);
				responseInfo.setUpMinTime(1440);
				responseInfo.setPrice(1.0F);
			}
			session.setAttribute(DEVICEINFO, responseInfo);
			return responseInfo;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("acol-scada-manage服务器出错 无法发现该应用："+ e.getMessage());
			return null;
		}
	}


	@Override
	public void submitLastRecords(Session session) {
		// TODO Auto-generated method stub
		UpRecord upRecord = (UpRecord) session.getAttribute(LASTRECORD);
		if(upRecord !=null) {
			LastRecordDto lastRecordDto = new LastRecordDto();
			lastRecordDto.setColTime(upRecord.getColTime().getTime());
			lastRecordDto.setDeviceVolt(upRecord.getDeviceVolt());
			lastRecordDto.setMeterVolt(upRecord.getMeterVolt());
			lastRecordDto.setPress(upRecord.getPress());
			lastRecordDto.setScFlow(upRecord.getScFlow());
			lastRecordDto.setScSum(upRecord.getScSum());
			lastRecordDto.setSignal(upRecord.getSignal());
			lastRecordDto.setTemp(upRecord.getTemp());
			lastRecordDto.setValueStatus(upRecord.getValueStatus());
			lastRecordDto.setWcFlow(upRecord.getWcFlow());
			lastRecordDto.setWcSum(upRecord.getWcSum());
			try {
				SimpleResponse response = restTemplate.postForObject(discoveryUtil.getAcolScadaManageUrl()+"/device/"+upRecord.getScaNum()+"/lastRecord", lastRecordDto, SimpleResponse.class);
				if(response.getErrorCode() != 1) {
					log.error("服务器数据提交成功但是没有保存下来错误码：" + response.getErrorCode()+ "错误信息"+response.getMessage());
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				log.error("请求服务器 添加最后一条记录失败：" +e.getMessage());
			}
		}
		//UpRecordDto upRecordDto = new UpRecordDto();
	//	Utils.changeUpRecordToUpRecordDto(upRecordDto, lastUpRecord);
		//
	}

	@Override
	public void flushLastRecords(UpRecord upRecord, Session session) {
		// TODO Auto-generated method stub
		session.setAttribute(LASTRECORD, upRecord);
	}
}
