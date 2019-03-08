package cn.acol.scada.service.utils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.NullArgumentException;

import cn.acol.scada.core.domain.ScadaStatus;
import cn.acol.scada.core.dto.DeviceType;
import cn.acol.scada.core.dto.LastRecordDto;
import cn.acol.scada.core.dto.PricePlanDto;
import cn.acol.scada.core.dto.ScaParamsDto;
import cn.acol.scada.core.dto.ScadaDto;
import cn.acol.scada.core.dto.UserDto;
import cn.acol.scada.domain.LastRecord;
import cn.acol.scada.domain.PricePlan;
import cn.acol.scada.domain.Scada;
import cn.acol.scada.domain.ScadaParams;
import cn.acol.scada.domain.User;

public class Utils {
	/**
	 * 将lastRecordDto 存入 lastRecord中
	 * @param lastRecordDto
	 * @param lastRecord
	 */
	public static void LastRecordDtoToLastRecord(LastRecordDto lastRecordDto,LastRecord lastRecord) {
		if(lastRecordDto==null||lastRecord == null) {
			throw new NullArgumentException("参数不能为空");
		}
		try {
			BeanUtils.copyProperties(lastRecord, lastRecordDto);
		} catch (IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
	}
	/**
	 * Scada 转为ScadaDto 不带用户信息
	 * @param scadaDto
	 * @param scada
	 */
	public static void ScadaDtoToScada(ScadaDto scadaDto, Scada scada) {
		if(scadaDto==null||scada == null) {
			throw new NullArgumentException("参数不能为空");
		}
		try {
			BeanUtils.copyProperties(scada, scadaDto);
		} catch (IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
	}
	/**
	 * Scada 转为ScadaDto 不带用户信息
	 * @param scada
	 * @param scadaDto
	 */
	public static void ScadaToScadaDto(Scada scada, ScadaDto scadaDto) {
		if(scadaDto==null||scada == null) {
			throw new NullArgumentException("参数不能为空");
		}
		scadaDto.setScaNum(scada.getScaNum());//采集器编号
		scadaDto.setColType(scada.getColType());
		Date lastConnetionTime = scada.getLastConnetionTime();
		if(lastConnetionTime != null) {
			scadaDto.setLastConTime(scada.getLastConnetionTime().getTime());
		}
		
		DeviceType deviceType = scada.getDeviceType();
		scadaDto.setDeviceType(deviceType);
		if(deviceType == DeviceType.PrepaymentIC) {
			ScadaParams scadaParams = scada.getScadaParams();
			scadaDto.setPricePlan(scadaParams.getPricePlan().getPlanName());//价格方案名称
			scadaDto.setValueStatus(scadaParams.isDesValueStatus());//是否为开阀门状态
		}
		LastRecord lastRecord = scada.getLastRecord();
		if(lastRecord !=null) {
			scadaDto.setTemp(lastRecord.getTemp());
			scadaDto.setPress(lastRecord.getPress());
			scadaDto.setScFlow(lastRecord.getScFlow());
			scadaDto.setScSum(lastRecord.getScSum());
			scadaDto.setSurplusSum(lastRecord.getSurplusSum());//剩余气量
			scadaDto.setMeterVolt(lastRecord.getMeterVolt());//芯版电池电压
			scadaDto.setDeviceVolt(lastRecord.getDeviceVolt());//设备电压
		
			
		}
	}
	/**
	 *不带用户信息
	 * @param scadas
	 * @return
	 */
	public static List<ScadaDto> changeScadasToScadaDtos(List<Scada> scadas){
		List<ScadaDto> scadaDtos = new ArrayList<>();
		for(Scada scada : scadas) {
			ScadaDto scadaDto = new ScadaDto();
			ScadaToScadaDto(scada, scadaDto);
			scadaDtos.add(scadaDto);
		}
		return scadaDtos;
	}

	
	/**
	 * 将user 转为userDto 不伴随采集器信息
	 * @param user
	 * @param userDto
	 */
	public static void changeUserToUserDtoWithoutScada(User user, UserDto userDto) {
		if(user==null||userDto == null) {
			throw new NullArgumentException("参数不能为空");
		}
		userDto.setId(user.getId());
		userDto.setCustomerAddress(user.getCustomerAddress());
		userDto.setCustomerName(user.getCustomerName());
	}
	
	public static void changeUserDtoToUserWithoutScada(UserDto userDto,User user) {
		if(user==null||userDto == null) {
			throw new NullArgumentException("参数不能为空");
		}
		user.setCustomerAddress(userDto.getCustomerAddress());
		user.setCustomerName(userDto.getCustomerName());
	}
	
	public static void changeScaParamsToScaParamsDto(ScadaParams scadaParams, ScaParamsDto scaParamsDto) {
		if(scadaParams == null || scaParamsDto == null) {
			throw new NullArgumentException("参数不能为空");
		}
		try {
			BeanUtils.copyProperties(scaParamsDto, scadaParams);
			
			if(scadaParams.getChangeDate()!=null) {
				scaParamsDto.setChangeDatel(scadaParams.getChangeDate().getTime());
			}
			
			if(scadaParams.getSynchronizationDate()!=null) {
				scaParamsDto.setSynchronizationDatel(scadaParams.getSynchronizationDate().getTime());
			}
			if(scadaParams.getPricePlan() != null) {
				PricePlan pricePlan = scadaParams.getPricePlan();
				PricePlanDto pricePlanDto = new PricePlanDto();
				pricePlanDto.setPriceType(pricePlan.getPriceType());
				pricePlanDto.setPlanName(pricePlan.getPlanName());
				pricePlanDto.setId(pricePlan.getId());
				pricePlanDto.setPrice(pricePlan.getPrice());
				pricePlanDto.setExDate(pricePlan.getExDate().getTime());
				scaParamsDto.setPricePlanDto(pricePlanDto);
			}
			if(scadaParams.getExPricePlan() !=null) {
				PricePlan pricePlan = scadaParams.getExPricePlan();
				PricePlanDto pricePlanDto = new PricePlanDto();
				pricePlanDto.setPlanName(pricePlan.getPlanName());
				pricePlanDto.setPriceType(pricePlan.getPriceType());
				pricePlanDto.setId(pricePlan.getId());
				pricePlanDto.setPrice(pricePlan.getPrice());
				pricePlanDto.setExDate(pricePlan.getExDate().getTime());
				scaParamsDto.setExPricePlanDto(pricePlanDto);
			}
			
		} catch (IllegalAccessException  | InvocationTargetException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
	}
	public static void changeScadaParamsDtoToScaParams(ScaParamsDto scaParamsDto,ScadaParams scadaParams) {
		if(scadaParams == null || scaParamsDto == null) {
			throw new NullArgumentException("参数不能为空");
		}
		try {
			BeanUtils.copyProperties(scadaParams, scaParamsDto);
		}catch (IllegalAccessException  | InvocationTargetException e) {
			// TODO: handle exception
			throw new RuntimeException(e);
		}
	}
	
	public static void main(String[] args) {
		
		ScadaParams scadaParams = new ScadaParams();
		scadaParams.setId(32L);
		scadaParams.setUpTime(42);
		scadaParams.setColTime(342);
		ScaParamsDto scaParamsDto = new ScaParamsDto();
		changeScaParamsToScaParamsDto(scadaParams, scaParamsDto);
		System.out.println(scaParamsDto.getUpTime());
	}
}
