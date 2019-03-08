package cn.acol.scada.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.acol.scada.core.dto.LastRecordDto;
import cn.acol.scada.core.dto.ScaParamsDto;
import cn.acol.scada.core.dto.SimpleResponse;
import cn.acol.scada.service.ScadaService;

@RestController
@RequestMapping("/device")
public class DeviceController {
	@Autowired
	private ScadaService scadaService;
	/**
	 * 可获取到采集器的参数
	 * @param scaNum
	 * @return
	 */
	@GetMapping("/{scaNum}/scaParams")
	public ScaParamsDto getScaParams(@PathVariable String scaNum) {
		return scadaService.getParams(scaNum);
	}
	/**
	 * 更新采集器最后一条记录
	 * @param deviceNum
	 * @param lastRecordDto
	 * @return
	 */
	@PostMapping("/{deviceNum}/lastRecord")
	public SimpleResponse submitLastRecord(@PathVariable String deviceNum, @RequestBody LastRecordDto lastRecordDto) {
		
		int updateRecords = scadaService.updateRecords(lastRecordDto, deviceNum);
		
		return new SimpleResponse(updateRecords, "-1 为表号不存在  0为存在却修改失败  1为成功");
		
	}
	@PostMapping("/{deviceNum}/scaParams")
	public SimpleResponse submitScaParams(@PathVariable String deviceNum, @RequestBody ScaParamsDto scaParamsDto) {
		if(!scaParamsDto.getDeviceNum().equals(deviceNum)) {
			return new SimpleResponse(-1,"设备号校验错误错误");
		}
		return scadaService.submitParams(scaParamsDto);
	}
}
