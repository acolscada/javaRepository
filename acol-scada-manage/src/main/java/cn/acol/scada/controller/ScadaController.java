package cn.acol.scada.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.acol.scada.core.dto.LastRecordDto;
import cn.acol.scada.core.dto.ScaParamsDto;
import cn.acol.scada.core.dto.ScadaDto;
import cn.acol.scada.core.dto.SimpleResponse;
import cn.acol.scada.core.dto.UpRecordDto;
import cn.acol.scada.service.CompanyService;
import cn.acol.scada.service.ScadaService;

@RestController
@RequestMapping("/scada")
public class ScadaController {
	@Autowired
	private ScadaService scadaService;
	@Autowired
	private CompanyService companyService;
	/**
	 * @param scaNum
	 * @return  errorcode = -1 编号错误
	 * 						1  存在
	 * 						0 不存在
	 */
	@GetMapping("/exist/{scaNum}")
	public SimpleResponse isExist(@PathVariable String scaNum) {
		if(scadaService.isExistScada(scaNum)) {
			return new SimpleResponse(1,scaNum);
		}else {
			return new SimpleResponse(0,scaNum);
		}
	}
	
	@GetMapping("/{scaNum}")
	public ScadaDto getScadaInfo(@PathVariable String scaNum) {
		return scadaService.getScadaByScaNum(scaNum);
	}
	/**
	 * 添加一台采集器
	 * @param scadaDto
	 * @return
	 */
	@PostMapping
	public SimpleResponse addScadaInfo(@RequestBody ScadaDto scadaDto) {
		return companyService.addDevice(scadaDto);
	}
	
	/**
	 *  修改采集器某次记录的信息
	 *  采集器不存在errorCode = -1
	 * 采集器存在但修改失败则 errorCode = 0
	 * 采集器存在且修改成功则errorCode = 1
	 * @return
	 */
	@PutMapping("record/{scaNum}")
	public SimpleResponse updateScadaInfo(@RequestBody LastRecordDto lastRecordDto) {
		return null;
	}
	@PutMapping("upload/{scaNum}")
	public SimpleResponse updateTime(String scaNum){
		return null;
	}
	/**
	 * 获取所有设备信息
	 * @return
	 */
	@GetMapping
	public List<ScadaDto> getScadaDtos(){
		return scadaService.getDevices();
	}
	
	/**
	 * 可获取某台采集器上传记录信息
	 * @param scaNum
	 * @return
	 */
	@GetMapping("/{deviceNum}/records")
	public List<UpRecordDto> getUpRecords(@PathVariable String deviceNum){
		return scadaService.getUpRecords(deviceNum);
	}
	
	@GetMapping("/params")
	public List<ScaParamsDto> getParams(String searchString,String searchField){
		if((!StringUtils.isEmpty(searchField))&& searchField.equals("deviceNum")&& (!StringUtils.isEmpty(searchString)) ) {
			return scadaService.getParamsbyDeviceNum(searchString);
		}
		return scadaService.getAllParams();
	}
	@PostMapping("/params")
	public SimpleResponse setParams(ScaParamsDto scaParamsDto) {
		SimpleResponse response = scadaService.upDateParams(scaParamsDto);
		return response;
	}
	@GetMapping("{deviceNum}/status/{value}")
	public SimpleResponse setStatus(@PathVariable String deviceNum,@PathVariable boolean value) {
		return scadaService.status(deviceNum, value);
	}
}
