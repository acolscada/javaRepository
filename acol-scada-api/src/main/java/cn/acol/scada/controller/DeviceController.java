package cn.acol.scada.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import cn.acol.scada.core.dto.LastRecordDto;
import cn.acol.scada.core.dto.ScaParamsDto;
import cn.acol.scada.core.dto.SimpleResponse;
import cn.acol.scada.discovery.Discovery;

@RestController
@RequestMapping("/device")
public class DeviceController {
	private RestTemplate restTemplate;
	private Discovery discovery;

	/**
	 * 可获取到采集器的参数
	 * @param scaNum
	 * @return
	 */
	@GetMapping("/{deviceNum}/scaParams")
	public ScaParamsDto getScaParams(@PathVariable String deviceNum) {
		return restTemplate.getForObject(discovery.getScadaManageUrl()+"/"+deviceNum+"/scaParams", ScaParamsDto.class);
	}
	/**
	 * 更新采集器最后一条记录
	 * @param deviceNum
	 * @param lastRecordDto
	 * @return
	 */
	@PostMapping("/{deviceNum}/lastRecord")
	public SimpleResponse submitLastRecord(@PathVariable String deviceNum, @RequestBody LastRecordDto lastRecordDto) {
		return restTemplate.postForObject(discovery.getScadaManageUrl()+"/"+deviceNum+"/lastRecord", lastRecordDto, SimpleResponse.class);
	}
	/*
	@PostMapping("/{deviceNum}/scaParams")
	public SimpleResponse submitScaParams(@PathVariable String deviceNum, @RequestBody ScaParamsDto scaParamsDto) {
		if(!scaParamsDto.getDeviceNum().equals(deviceNum)) {
			return new SimpleResponse(-1,"设备号校验错误错误");
		}
		return scadaService.submitParams(scaParamsDto);
	}*/
}
