package cn.acol.oldscada.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.acol.oldscada.service.OldScadaService;
import cn.acol.scada.core.dto.ScadaDto;

@RestController
@RequestMapping("/scada")
public class OldScadaController {
	@Autowired
	private OldScadaService oldScadaService;
	@GetMapping
	public List<ScadaDto> getAll(){
		return oldScadaService.getAllScadas();
	}
	
	
	@RequestMapping("/{parentId}")
	@GetMapping
	public List<ScadaDto> getCollectorsByParentId(@PathVariable Long parentId){
		return oldScadaService.getScadasByUserId(parentId);
	}
}
