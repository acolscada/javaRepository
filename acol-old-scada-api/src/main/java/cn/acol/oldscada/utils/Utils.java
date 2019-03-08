package cn.acol.oldscada.utils;



import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.NullArgumentException;
import org.springframework.data.domain.Page;

import cn.acol.oldscada.domain.primary.InfoCollector;
import cn.acol.oldscada.domain.primary.InfoCustomer;
import cn.acol.oldscada.domain.secondary.IoData;
import cn.acol.scada.core.dto.PageBean;
import cn.acol.scada.core.dto.ScadaDto;
import cn.acol.scada.core.dto.UpRecordDto;
import cn.acol.scada.core.dto.UserDto;
import cn.acol.scada.core.utils.CUtils;

public class Utils {
	private static void InfoCollectorToScadaDto(InfoCollector infoCollector,ScadaDto scadaDto) {
		if(infoCollector==null || scadaDto == null) {
			throw new NullArgumentException("参数不能为空");
		}
		scadaDto.setColType("RS485");
		Date updatetime = infoCollector.getUpdatetime();
		if(updatetime != null) {
			scadaDto.setColTime(infoCollector.getUpdatetime().getTime());
		}
		scadaDto.setScaNum(infoCollector.getCode());
		scadaDto.setMeterVolt(infoCollector.getDcdy());
		scadaDto.setPress(infoCollector.getYl());
		scadaDto.setScSum((double) infoCollector.getBkzl());
		scadaDto.setSignal((int)infoCollector.getXhqd());
		scadaDto.setWcSum((double) infoCollector.getGkzl());
		scadaDto.setTemp(infoCollector.getWd());
		scadaDto.setScFlow(infoCollector.getBkll());
		scadaDto.setWcFlow(infoCollector.getGkll());
	}
	private static void InfoCustomerToUserDto(InfoCustomer infoCustomer,UserDto userDto) {
		if(infoCustomer == null || userDto == null) {
			throw new NullArgumentException("参数不能为空");
		}
		userDto.setId((long)infoCustomer.getID());
		userDto.setCustomerName(infoCustomer.getName());
	}
	
	public static List<UserDto> changeInfoCustomersToUserDtos(List<InfoCustomer> infoCustomers) {
		List<UserDto> userDtos = new ArrayList<>();
		for(InfoCustomer infoCustomer : infoCustomers) {
			UserDto userDto = new UserDto();
			InfoCustomerToUserDto(infoCustomer, userDto);
			userDtos.add(userDto);
		}
		return userDtos;
	}
	public static List<ScadaDto> changeInfoCollectorsToScadaDtos(List<InfoCollector> infoCollectors) {
		List<ScadaDto> scadas = new ArrayList<>();
		for(InfoCollector infoCollector : infoCollectors) {
			ScadaDto scadaDto = new ScadaDto();
			InfoCollectorToScadaDto(infoCollector, scadaDto);
			scadas.add(scadaDto);
		}
		return scadas;
	}
	private static void IoDataToUpRecordDto(IoData ioData,UpRecordDto upRecordDto) {
		if(ioData == null||upRecordDto==null) {
			throw new NullArgumentException("参数不能为空");
		}
		upRecordDto.setColTime(ioData.getRecordTime().getTime());
		upRecordDto.setMeterVolt(ioData.getDcdy());
		upRecordDto.setPress(ioData.getYl());
		upRecordDto.setScSum(ioData.getBkzl());
		upRecordDto.setSignal((int)ioData.getXhqd());
		upRecordDto.setWcSum(ioData.getGkzl());
		upRecordDto.setTemp(ioData.getWd());
		upRecordDto.setUpTime(ioData.getLogTime().getTime());
		upRecordDto.setScFlow(ioData.getBkll());
		upRecordDto.setWcFlow(ioData.getGkll());
	}
	public static List<UpRecordDto> changeIoDatasToUpRecordDtos(List<IoData> ioDatas) {
		List<UpRecordDto> upRecordDtos = new ArrayList<>();
		for(IoData ioData : ioDatas) {
			UpRecordDto upRecordDto = new UpRecordDto();
			IoDataToUpRecordDto(ioData,upRecordDto);
			upRecordDtos.add(upRecordDto);
		}
		return upRecordDtos;
	}
	public static void ChangeIoDataPageToUpRecordPage(Page<IoData> ioDataPage, PageBean<UpRecordDto> upRecordPage) {
		CUtils.changePageToPageBean(ioDataPage, upRecordPage);
		List<UpRecordDto> upRecordDtos = changeIoDatasToUpRecordDtos(ioDataPage.getContent());
		upRecordPage.setProducts(upRecordDtos);
	}
}
