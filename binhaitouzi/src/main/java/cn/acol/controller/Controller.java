package cn.acol.controller;

import java.util.Date;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.acol.bhServer.core.Request;
import cn.acol.bhServer.core.exception.ConnectionException;
import cn.acol.device.test.core.DeviceMap;
import cn.acol.scada.core.dto.PricePlanDto;
import cn.acol.scada.core.dto.PriceType;
import cn.acol.scada.core.dto.ScaParamsDto;
import cn.acol.scada.core.dto.SimpleResponse;
import cn.acol.scada.core.exception.AnaysisException;

@RestController
@RequestMapping("/testController")
public class Controller {
	/**
	 *获取所有连接的设备号
	 * @return
	 */
	@GetMapping("/getDevices")
	public List<String> getDevices(){
		return DeviceMap.getAllDevices();
	}
	@GetMapping("/{deviceNum}/closeCon")
	public SimpleResponse closeCon(@PathVariable String deviceNum) {
		Request requestByDeviceNum = DeviceMap.getRequestByDeviceNum(deviceNum);
		if(requestByDeviceNum == null) {
			return SimpleResponse.getErrorResponse("设备号为："+deviceNum+"   未找到此设备的连接");
		}
		SimpleResponse closeConnection = requestByDeviceNum.closeConnection();
		DeviceMap.moveDevice(deviceNum);
		if(closeConnection.isOk()) {
			closeConnection.setMessage(deviceNum);
		}
		return closeConnection;
	}
	@PostMapping("/{deviceNum}/status")
	public SimpleResponse status(boolean statusValue,@PathVariable String deviceNum) {
		 Request requestByDeviceNum = DeviceMap.getRequestByDeviceNum(deviceNum);
		 if(requestByDeviceNum == null) {
				return SimpleResponse.getErrorResponse("设备号为："+deviceNum+"   未找到此设备的连接");
		 }
		 if(statusValue) {//开发
			 try {
				SimpleResponse closeValueStatus = requestByDeviceNum.closeValueStatus();
				closeValueStatus.setMessage("时间："+new Date()+"   设备号为："+deviceNum+"   开阀成功");
				return closeValueStatus;
			} catch (ConnectionException e) {
				// TODO Auto-generated catch block
				return SimpleResponse.getErrorResponse(e.getMessage());
			}
		 }else {
			try {
				SimpleResponse openValueStatus = requestByDeviceNum.openValueStatus();
				openValueStatus.setMessage("时间："+new Date()+"   设备号为："+deviceNum+"   关阀成功");
				return openValueStatus;
			} catch (ConnectionException e) {
				// TODO Auto-generated catch block
				return SimpleResponse.getErrorResponse(e.getMessage());
			}
		 }
	}
	/**
	 * 价格方案为
	 */
	@PostMapping("/{deviceNum}/price")
	 public SimpleResponse changPrice(@PathVariable String deviceNum,float price,int exMin,float exPrice) {
		 Request requestByDeviceNum = DeviceMap.getRequestByDeviceNum(deviceNum);
		 if(requestByDeviceNum == null) {
				return SimpleResponse.getErrorResponse("设备号为："+deviceNum+"   未找到此设备的连接");
		 }
		 if(price == 0) {
			 return SimpleResponse.getErrorResponse("当前价格必须存在且不为0");
		 }
		 PricePlanDto pricePlanDto = new PricePlanDto();
		 PricePlanDto exPricePlanDto = new PricePlanDto();
		 pricePlanDto.setExDate(10L);
		 pricePlanDto.setPrice(price);
		 pricePlanDto.setPriceType(PriceType.Normal);
		 if(exMin >0) {
			 exPricePlanDto.setExDate(new Date().getTime()+exMin*1000*60);
			 exPricePlanDto.setPrice(exPrice);
			 exPricePlanDto.setPriceType(PriceType.Normal);
		 }else {
			 exPricePlanDto = null;
		 }
		try {
			SimpleResponse changePrice = requestByDeviceNum.changePrice(pricePlanDto , exPricePlanDto);
			if(changePrice.isOk()) {
				changePrice.setMessage("时间："+new Date()+"   设备号为："+deviceNum+"   设备价格修改为："+ pricePlanDto.getPrice());
				return changePrice;
			}else {
				return changePrice;
			}
		}catch (ConnectionException e) {
			// TODO Auto-generated catch block
			return SimpleResponse.getErrorResponse(e.getMessage());
		} catch (AnaysisException e) {
			// TODO Auto-generated catch block
			return SimpleResponse.getErrorResponse(e.getMessage());
		}
	 }
	 
	 
	/**
	 * 修改采集间隔
	 */
	@PostMapping("/{deviceNum}/colTime")
	public SimpleResponse changeColTime(ScaParamsDto scaParamsDto,@PathVariable String deviceNum) {
		if(scaParamsDto ==null || scaParamsDto.getDesColTime() == 0) {
			return SimpleResponse.getErrorResponse("API参数设计错误： 确保存在desColTime 且值不为0");
		}
		Request requestByDeviceNum = DeviceMap.getRequestByDeviceNum(deviceNum);
		if(requestByDeviceNum == null) {
			return SimpleResponse.getErrorResponse("无法充值：设备号为："+deviceNum+"   未找到此设备的连接");
		}
		try {
			SimpleResponse changeUpTime = requestByDeviceNum.changeColTime(scaParamsDto.getDesColTime());
			if(changeUpTime.isOk()) {
				changeUpTime.setMessage("时间："+new Date()+"   设备号为："+deviceNum+"   采集间隔修改为："+ scaParamsDto.getDesColTime()+"分钟");
				return changeUpTime;
			}else {
				return changeUpTime;
			}
		} catch (ConnectionException e) {
			// TODO Auto-generated catch block
			return SimpleResponse.getErrorResponse(e.getMessage());
		} 
	}
	
	/**
	 * 修改上传间隔
	 */
	@PostMapping("/{deviceNum}/upTime")
	public SimpleResponse changeUpTime(ScaParamsDto scaParamsDto, @PathVariable String deviceNum) {
		if(scaParamsDto ==null || scaParamsDto.getDesUpTime()==0) {
			return SimpleResponse.getErrorResponse("API参数设计错误： 确保存在desUpTime 且值不为0");
		}
		
		Request requestByDeviceNum = DeviceMap.getRequestByDeviceNum(deviceNum);
		if(requestByDeviceNum == null) {
			return SimpleResponse.getErrorResponse("无法充值：设备号为："+deviceNum+"   未找到此设备的连接");
		}
		
		try {
			SimpleResponse changeUpTime = requestByDeviceNum.changeUpTime(scaParamsDto.getDesUpTime());
			if(changeUpTime.isOk()) {
				changeUpTime.setMessage("时间："+new Date()+"   设备号为："+deviceNum+"   上传间隔修改为："+ scaParamsDto.getUpTime()+"分钟");
				return changeUpTime;
			}else {
				return changeUpTime;
			}
		} catch (ConnectionException e) {
			// TODO Auto-generated catch block
			return SimpleResponse.getErrorResponse(e.getMessage());
		} catch (AnaysisException e) {
			// TODO Auto-generated catch block
			return SimpleResponse.getErrorResponse(e.getMessage());
		}
	}
	/**
	 *为设备充值金钱
	 */
	@PostMapping("/{deviceNum}/addMoney")
	public SimpleResponse addMoney(ScaParamsDto scaParamsDto,@PathVariable String deviceNum) {
		if(scaParamsDto ==null || scaParamsDto.getDesAddPlusSum()==null || scaParamsDto.getTotalRecharge() == null) {
			return SimpleResponse.getErrorResponse("API参数设计错误： 确保存在desAddPlusSum 和 totalRecharge");
		}
		if(scaParamsDto.getDesAddPlusSum() != 0) {
			Request requestByDeviceNum = DeviceMap.getRequestByDeviceNum(deviceNum);
			if(requestByDeviceNum == null) {
				return SimpleResponse.getErrorResponse("无法充值：设备号为："+deviceNum+"   未找到此设备的连接");
			}
			
			try {
				SimpleResponse addPlusSum = requestByDeviceNum.addPlusSum(scaParamsDto.getDesAddPlusSum(), scaParamsDto.getTotalRecharge());
				if(addPlusSum.isOk()) {
					addPlusSum.setMessage("充值成功: 时间："+new Date()+"设备号为："+deviceNum+"    充值了："+scaParamsDto.getDesAddPlusSum());
					return addPlusSum;
				}else {
					return addPlusSum;
				}
			} catch (ConnectionException e) {
				// TODO Auto-generated catch block
				return SimpleResponse.getErrorResponse(e.getMessage());
			}
		}
		return SimpleResponse.getErrorResponse("请节约流量  有充值0元但指令没有下发");
	}
}
