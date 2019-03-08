package cn.acol.scada.service;

import java.util.List;

import cn.acol.scada.core.dto.CompanyDto;
import cn.acol.scada.core.dto.ScadaDto;
import cn.acol.scada.core.dto.SimpleResponse;
import cn.acol.scada.core.dto.UserDto;
import cn.acol.scada.domain.Company;
import cn.acol.scada.domain.User;

public interface CompanyService {
	/**
	 *对公司增加一个用户
	 * @param userDto
	 * @return
	 */
	public User addUser(UserDto userDto);
	/**
	 * 新增一家公司
	 * @param companyDto
	 * @return
	 */
	public Company addCompany(CompanyDto companyDto,String adminName);
	/**
	 * 获取所有公司信息
	 * @return
	 */
	public List<CompanyDto> getCompanys();
	/**
	 * 删除公司
	 * @param id
	 */
	public void deleteCompany(Long id);
	
	/**
	 * 为公司添加一个设备
	 * @param scadadto
	 * @return
	 */
	public SimpleResponse addDevice(ScadaDto scadaDto);
}
