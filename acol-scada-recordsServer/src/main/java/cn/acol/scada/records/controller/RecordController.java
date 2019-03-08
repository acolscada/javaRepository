package cn.acol.scada.records.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.acol.scada.core.dto.SimpleResponse;
import cn.acol.scada.core.dto.UpRecordDto;
import cn.acol.scada.records.service.DeviceService;


@RestController
@RequestMapping("/device")
public class RecordController {
	@Autowired
	private DeviceService deviceService;
	@GetMapping("/{deviceNum}")
	public List<UpRecordDto> upRecords(@PathVariable String deviceNum) {
		return deviceService.getDevices(deviceNum);
	}
	@PostMapping("/{deviceNum}/records")//增加记录
	public SimpleResponse addRecords(@RequestBody List<UpRecordDto> upRecordDtos,@PathVariable String deviceNum) {
		return deviceService.saveRecords(upRecordDtos,deviceNum);
	}
}
