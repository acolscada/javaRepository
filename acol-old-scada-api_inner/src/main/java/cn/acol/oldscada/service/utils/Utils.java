package cn.acol.oldscada.service.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.NullArgumentException;
import org.springframework.data.domain.Page;

import cn.acol.oldscada.domain.IoData;
import cn.acol.scada.core.dto.PageBean;
import cn.acol.scada.core.dto.UpRecordDto;
import cn.acol.scada.core.utils.CUtils;

public class Utils {
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
