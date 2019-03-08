package cn.acol.scada.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import cn.acol.scada.core.dto.CompanyDto;
import cn.acol.scada.core.dto.SimpleResponse;
import cn.acol.scada.core.utils.AdminUtils;
import cn.acol.scada.core.utils.DiscoveryUtil;
import cn.acol.scada.discovery.Discovery;

@RestController
@RequestMapping("/company")
public class CompanyController {
	private RestTemplate restTemplate;
	private Discovery discovery;
	/*@PostMapping
	public SimpleResponse addCompany(@RequestBody CompanyDto companyDto) {
		return restTemplate.postForObject(discovery.getScadaManageUrl()+"/", companyDto, SimpleResponse.class);
	}
	@GetMapping
	public List<CompanyDto> getCompanys(){
		return companyService.getCompanys();
	}
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		companyService.deleteCompany(id);
	}*/
}
