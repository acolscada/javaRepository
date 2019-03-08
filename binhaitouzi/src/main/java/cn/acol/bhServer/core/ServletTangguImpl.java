package cn.acol.bhServer.core;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import cn.acol.bhServer.core.exception.ConnectionException;
import cn.acol.scada.core.dto.LastRecordDto;
import cn.acol.scada.core.dto.PricePlanDto;
import cn.acol.scada.core.dto.PriceType;
import cn.acol.scada.core.dto.ScaParamsDto;
import cn.acol.scada.core.dto.SimpleResponse;
import cn.acol.scada.core.dto.UpRecordDto;
import cn.acol.scada.core.utils.DiscoveryUtil;

@Component
public class ServletTangguImpl implements Servlet{
	
	private static final Logger log = LoggerFactory.getLogger(ServletTangguImpl.class);
	
	private ScaParamsDto scaParamsDto;
	
	synchronized private void setScaParamsDto(String deviceNum) {
		try {
			String url = discoveryUtil.getAcolScadaManageUrl();
			scaParamsDto = restTemplate.getForObject(url+"/device/"+deviceNum+"/scaParams", ScaParamsDto.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("无法请求参数信息： "+e.getMessage());
			throw new RuntimeException();
		}
	}
	synchronized private ScaParamsDto getScaParamsDto(){
		if(scaParamsDto == null) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				log.error("线程无法睡眠 :"+e.getMessage());
			}
		}
		return scaParamsDto;
	}
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private DiscoveryUtil discoveryUtil;
	//可以注入任意组件
	public void initHandler(Request request) {
		//计时30s如果没有数据上传则使用request请求远程控制
		System.out.println("ICCID:  "+  request.getSession().getICCID()+"  设备电压："+request.getSession().getVolt());
	}
	private SimpleResponse saveUpRecords(List<UpRecordDto> upRecordDtos,String deviceNum) {
		if(upRecordDtos ==null || upRecordDtos.size() == 0) {
			return SimpleResponse.getNormalResponse();
		}
		UpRecordDto[] upRecordDtos2 = new UpRecordDto[upRecordDtos.size()];
		upRecordDtos.toArray(upRecordDtos2);
		String url = null;
		try {
			url = discoveryUtil.getRecordsUrl();
			return restTemplate.postForObject(url+"/device/"+deviceNum+"/records", upRecordDtos2, SimpleResponse.class);
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			log.error("检查添加记录服务器");
			return new SimpleResponse(-1,"检查记录服务器地址: "+url);
		}
	}
	private LastRecordDto getLastRecordDto(List<UpRecordDto> upRecordDtos) {
		if(upRecordDtos == null || upRecordDtos.size() == 0) {
			return null;
		}
		UpRecordDto upRecord = upRecordDtos.get(upRecordDtos.size()-1);
		LastRecordDto lastRecordDto = new LastRecordDto();
		lastRecordDto.setColTime(upRecord.getColTime());
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
		return lastRecordDto;
	}
	String deviceNum;
	public void upRecordsHandler(Response response,Request request) {
			/**
			 * 因为塘沽卡片中 表号没有故而使用用户号代替
			 */
			log.info("用户号为： "+response.getUserNum());
			
		    deviceNum = response.getDeviceNum();
		    
			if(deviceNum.equals("0000000000000000")){
				deviceNum = response.getUserNum();
			}
			log.info("通讯使用的设备号为： "+ deviceNum);
		
			
			
			
			
			new Thread(new Runnable() {//进来时不管如何先设置参数信息
				@Override
				public void run() {
					// TODO Auto-generated method stub
					setScaParamsDto(deviceNum);
				}
			}).start();
			
			/**
			 * 更新设备时间
			 */
			try {
				SimpleResponse upDateTime = request.upDateTime();
				if(!upDateTime.isOk()) {
					log.info("设备号为："+deviceNum+"的设备更新时间失败原因为： "+upDateTime.getMessage());
				}
				
				/**/
			} catch (ConnectionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//获取参数信息
			List<UpRecordDto> upRecordDtos = response.getUpRecordDtos();
			ScaParamsDto scaParamsDto = getScaParamsDto();
			System.out.println(scaParamsDto);
			
			if(scaParamsDto!=null) {
				//设备存在
				new Thread(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						saveUpRecords(upRecordDtos,deviceNum);//保存上传记录
						//保存最后一条上传记录
						LastRecordDto lastRecordDto = getLastRecordDto(upRecordDtos);
						if(lastRecordDto !=null) {
							try {
								SimpleResponse simpleResponse = restTemplate.postForObject(discoveryUtil.getAcolScadaManageUrl()+"/device/"+deviceNum+"/lastRecord", lastRecordDto, SimpleResponse.class);
								if(simpleResponse.getErrorCode() != 1) {
									log.error("服务器数据提交成功但是没有保存下来错误码：" + simpleResponse.getErrorCode()+ "错误信息"+simpleResponse.getMessage());
								}
							} catch (RestClientException e) {
								e.printStackTrace();
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				}).start();
				System.out.println("获取需要的改变的价格");
				if(scaParamsDto.needChange()) {//
					System.out.println("价格改变了");
					try {
						
						SimpleResponse changePrice = request.changePrice(scaParamsDto.getPricePlanDto(), scaParamsDto.getExPricePlanDto());
						if(scaParamsDto.getDesAddPlusSum() != 0) {
							SimpleResponse commitPlusSumAndPrice = request.addPlusSum(scaParamsDto.getDesAddPlusSum(),scaParamsDto.getTotalRecharge());//发送充值指令
							if(commitPlusSumAndPrice.isOk()) {
								scaParamsDto.setTotalRecharge(new Double(scaParamsDto.getTotalRecharge()+scaParamsDto.getDesAddPlusSum()).longValue());//
								scaParamsDto.setDesAddPlusSum((double) 0);
							}
						}
						if(scaParamsDto.needChangeValueStatus()) {
							if(!scaParamsDto.isDesValueStatus()) {//关阀
								SimpleResponse closeResponse = request.closeValueStatus();
								if(!closeResponse.isOk()) {
									log.error("关阀失败： "+ closeResponse.getMessage());
								}else {
									scaParamsDto.setValueStatus(scaParamsDto.isDesValueStatus());
								}
							}else {//
								SimpleResponse openResponse = request.openValueStatus();
								if(!openResponse.isOk()) {
									log.error("开阀失败： "+ openResponse.getMessage());
								}else {
									scaParamsDto.setValueStatus(scaParamsDto.isDesValueStatus());
								}
							}
						}
						if(scaParamsDto.needChangeColTime()) {
							SimpleResponse changeColTime = request.changeColTime(scaParamsDto.getDesColTime());
							if(changeColTime.isOk()) {
								scaParamsDto.setColTime(scaParamsDto.getDesColTime());
							}
						}
						if(scaParamsDto.needChangeUpTime()) {
							SimpleResponse changeUpTime = request.changeUpTime(scaParamsDto.getDesUpTime());
							if(changeUpTime.isOk()) {
								scaParamsDto.setUpTime(scaParamsDto.getDesUpTime());
							}
						}
						//byte[] haha = {0x3c,0x00,0x0c,0x04,(byte) 0xdd,(byte) 0xc2,0x66,(byte) 0x8d,(byte) 0xae,0x08,0x6d,0x0b};
						//request.getSession().getOutputStream().write(haha);
						scaParamsDto.setDeviceNum(deviceNum);
						restTemplate.postForObject(discoveryUtil.getAcolScadaManageUrl()+"/device/"+deviceNum+"/scaParams", scaParamsDto, SimpleResponse.class);
						
					}catch (ConnectionException e) {
						log.info("控制命令出错："+e.getMessage());
					}catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				}
				SimpleResponse closeConnection = request.closeConnection();
				if(!closeConnection.isOk()) {
					log.error("断开连接失败： "+closeConnection.getMessage());
				}
			}else {
				/**
				 * 断开连接
				 */
				log.info("设备未注册 设备号为：" + deviceNum);
				SimpleResponse closeConnection = request.closeConnection();
				if(!closeConnection.isOk()) {
					log.info("设备号为："+deviceNum+"的设备断开连接失败  原因为： "+closeConnection.getMessage());
				}
			}
			// TODO: handle exception
			// TODO Auto-generated catch block
	}
}
