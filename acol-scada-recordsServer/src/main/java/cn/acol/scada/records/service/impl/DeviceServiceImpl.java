package cn.acol.scada.records.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.acol.scada.core.dto.SimpleResponse;
import cn.acol.scada.core.dto.UpRecordDto;
import cn.acol.scada.records.domain.UpRecord;
import cn.acol.scada.records.repository.RecordRepository;
import cn.acol.scada.records.service.DeviceService;
import cn.acol.scada.records.utils.Utils;

@Service
public class DeviceServiceImpl implements DeviceService{
	@Autowired
	private RecordRepository recordRepository;
	@Override
	public List<UpRecordDto> getDevices(String deciveNum) {
		// TODO Auto-generated method stub
		List<UpRecord> upRecords = recordRepository.findByScaNum(deciveNum);
		List<UpRecordDto> upRecordDtos = new ArrayList<UpRecordDto>();
		for(UpRecord upRecord :upRecords) {
			UpRecordDto upRecordDto = new UpRecordDto();
			Utils.changeUpRecordToUpRecordDto(upRecordDto, upRecord);
			upRecordDtos.add(upRecordDto);
		}
		return upRecordDtos;
	}
	@Override
	public SimpleResponse saveRecords(List<UpRecordDto> upRecordDtos,String deviceNum) {
		// TODO Auto-generated method stub
		 
		List<UpRecord> upRecords = new ArrayList<UpRecord>();
		for(UpRecordDto upRecordDto : upRecordDtos) {
			UpRecord upRecord = new UpRecord();
			Utils.changeUpRecordDtoToUpRecord(upRecord, upRecordDto);
			upRecord.setScaNum(deviceNum);
			upRecords.add(upRecord);
		}
		for(UpRecord upRecord :upRecords) {
			recordRepository.save(upRecord); //保存upRecord
		}
		return SimpleResponse.getNormalResponse();
	}
}
