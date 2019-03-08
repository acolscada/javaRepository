package cn.acol.scada.controller;


import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import cn.acol.scada.core.dto.LastRecordDto;
import cn.acol.scada.core.dto.ScaParamsDto;
import cn.acol.scada.core.dto.ScadaDto;
import cn.acol.scada.core.dto.SimpleResponse;
import cn.acol.scada.core.dto.UpRecordDto;
import cn.acol.scada.discovery.Discovery;

@RestController
@RequestMapping("/scada")
public class ScadaController {
	private RestTemplate restTemplate;
	private Discovery discovery;
	@GetMapping("/{deviceNum}")
	public ScadaDto getScadaInfo(@PathVariable String deviceNum) {
		return restTemplate.getForObject(discovery.getScadaManageUrl()+"/scada/"+deviceNum, ScadaDto.class);
	}
	/**
	 * 添加一台采集器
	 * @param scadaDto
	 * @return
	 */
	@PostMapping
	public SimpleResponse addScadaInfo(@RequestBody ScadaDto scadaDto) {
		return restTemplate.postForObject(discovery.getScadaManageUrl()+"/scada", scadaDto, SimpleResponse.class);
	}
	
	/**
	 * 获取所有设备信息
	 * @return
	 */
	@GetMapping
	public List<ScadaDto> getScadaDtos(){
		ScadaDto[] scadaDtos = restTemplate.getForObject(discovery.getScadaManageUrl()+"/scada", ScadaDto[].class);
		if(scadaDtos == null || scadaDtos.length == 0) {
			return null;
		}
		return  Arrays.asList(scadaDtos);
	}
	
	/**
	 * 可获取某台采集器上传记录信息
	 * @param scaNum
	 * @return
	 */
	/*@GetMapping("/{deviceNum}/records")
	public List<UpRecordDto> getUpRecords(@PathVariable String deviceNum){
		return scadaService.getUpRecords(deviceNum);
	}
	@GetMapping("/params")
	public List<ScaParamsDto> getParams(){
		return scadaService.getAllParams();
	}
	@PostMapping("/params")
	public SimpleResponse setParams(ScaParamsDto scaParamsDto) {
		System.out.println(scaParamsDto);
		System.out.println(scaParamsDto.getExPricePlanDto());
		SimpleResponse response = scadaService.upDateParams(scaParamsDto);
		return response;
	}*/
}
