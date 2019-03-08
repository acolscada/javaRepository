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

import cn.acol.scada.core.dto.CompanyDto;
import cn.acol.scada.core.dto.SimpleResponse;
import cn.acol.scada.core.utils.AdminUtils;
import cn.acol.scada.domain.Company;
import cn.acol.scada.service.CompanyService;

@RestController
@RequestMapping("/company")
public class CompanyController {
	@Autowired
	private CompanyService companyService;
	@PostMapping
	public SimpleResponse addCompany(@RequestBody CompanyDto companyDto) {
		//新增一家公司
		Company addCompany = companyService.addCompany(companyDto,AdminUtils.getUserName());
		if(addCompany == null) {
			return new SimpleResponse(-1, "添加失败");
		}
		return SimpleResponse.getNormalResponse();
	}
	@GetMapping
	public List<CompanyDto> getCompanys(){
		return companyService.getCompanys();
	}
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		companyService.deleteCompany(id);
	}
}
