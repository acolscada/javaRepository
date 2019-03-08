package cn.acol.oldscada.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.acol.oldscada.service.OldScadaService;
import cn.acol.scada.core.dto.UserDto;

@RestController
@RequestMapping("oldSys/custumers")
public class InfoCustomerController {
	@Autowired
	private OldScadaService oldScadaService;
	
	@GetMapping
	public List<UserDto> getCustomers(){
		return oldScadaService.getUsers();
	}
}
