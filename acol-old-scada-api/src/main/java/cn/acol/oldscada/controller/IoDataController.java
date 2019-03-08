package cn.acol.oldscada.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import cn.acol.oldscada.service.OldScadaService;
import cn.acol.scada.core.dto.PageBean;
import cn.acol.scada.core.dto.UpRecordDto;



@RestController
@RequestMapping("/scada")
public class IoDataController {
	@Autowired
	private OldScadaService oldScadaService;
	
	@GetMapping("/{scaNum}/records")
	public List<UpRecordDto> getAll(@PathVariable String scaNum){
		return oldScadaService.getUpRecordsByScadaNum(scaNum);
	}
	@GetMapping("/{scaNum}/pageRecords")
	public PageBean<UpRecordDto> getPageRecords(@PathVariable String scaNum,@RequestParam Integer pageSize,Integer pageNum){
		return oldScadaService.getPageUpRecords(scaNum, pageSize, pageNum);
	}
	
}
