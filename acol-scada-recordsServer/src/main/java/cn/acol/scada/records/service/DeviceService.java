package cn.acol.scada.records.service;

import java.util.List;

import cn.acol.scada.core.dto.SimpleResponse;
import cn.acol.scada.core.dto.UpRecordDto;

public interface DeviceService {
	/**
	 * 通过采集器编号获取上传记录
	 * @param deciveNum
	 * @return
	 */
	public List<UpRecordDto> getDevices(String deviceNum);
	/**
	 * 保存4条记录
	 * @param upRecordDtos
	 * @return
	 */
	public SimpleResponse saveRecords(List<UpRecordDto> upRecordDtos,String deviceNum);
	
}
